package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Livro;

public class LivroDao implements ICrud<Livro> {
	
	private GenericDao gDao;
	
	public LivroDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public Livro buscar(Livro livro) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome, lingua, ano FROM Livro WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, livro.getCodigo());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			livro.setCodigo(rs.getInt("codigo"));
			livro.setNome(rs.getString("nome"));
			livro.setLingua(rs.getString("lingua"));
			livro.setAno(rs.getInt("ano"));
		}
		rs.close();
		ps.close();
		return livro;
	}

	@Override
	public List<Livro> listar() throws SQLException, ClassNotFoundException {
		List<Livro> livros = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT codigo, nome, lingua ano FROM Livro" ;
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Livro livro = new Livro();
			livro.setCodigo(rs.getInt("codigo"));
			livro.setNome(rs.getString("nome"));
			livro.setLingua(rs.getString("lingua"));
			livro.setAno(rs.getInt("ano"));
			
			livros.add(livro);
		}
		rs.close();
		ps.close();
		return livros;
	}

	@Override
	public String inserir(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{Call Livro_Procedure(?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "I");
		cs.setInt(2, l.getCodigo());
		cs.setString(3,  l.getNome());
		cs.setString(4, l.getLingua());
		cs.setInt(5,  l.getAno());
		cs.registerOutParameter(6, Types.VARCHAR);
		
		cs.execute();
		String saida = cs.getString(6);
		
		cs.close();
		c.close();
		return saida;
	}

	@Override
	public String atualizar(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{Call Livro_Procedure(?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "U");
		cs.setInt(2, l.getCodigo());
		cs.setString(3,  l.getNome());
		cs.setString(4, l.getLingua());
		cs.setInt(5,  l.getAno());
		cs.registerOutParameter(6, Types.VARCHAR);
		
		cs.execute();
		String saida = cs.getString(6);
		
		cs.close();
		c.close();
		return saida;
	}

	@Override
	public String excluir(Livro l) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{Call Livro_Procedure(?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "D");
		cs.setInt(2, l.getCodigo());
		cs.setNull(3,  Types.VARCHAR);
		cs.setNull(4, Types.VARCHAR);
		cs.setNull(5,  Types.VARCHAR);
		cs.registerOutParameter(6, Types.VARCHAR);
		
		cs.execute();
		String saida = cs.getString(6);
		
		cs.close();
		c.close();
		return saida;
	}
}