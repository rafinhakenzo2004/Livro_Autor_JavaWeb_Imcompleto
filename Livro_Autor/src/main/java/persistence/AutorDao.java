package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Autor;

public class AutorDao implements ICrud<Autor> {
	
	private GenericDao gDao;
	
	public AutorDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public Autor buscar(Autor autor) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT id_Autor, nome, data_nasc FROM Autor WHERE id_Autor = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, autor.getId_autor());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			autor.setId_autor(rs.getInt("id_autor"));
			autor.setNome(rs.getString("nome"));
			autor.setData_nasc(LocalDate.parse(rs.getString("nascimento")));
			autor.setPais_nasc(rs.getString("pais_nascimento"));
			autor.setBiografia(rs.getString("biografia"));
		}
		rs.close();
		ps.close();
		return autor;
	}

	@Override
	public List<Autor> listar() throws SQLException, ClassNotFoundException {
		List<Autor> autores = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT id_Autor, nome, data_nasc, pais_nasc, biografia FROM Autor" ;
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Autor autor = new Autor();
			autor.setId_autor(rs.getInt("id_autor"));
			autor.setNome(rs.getString("nome"));
			autor.setData_nasc(LocalDate.parse(rs.getString("nascimento")));
			autor.setPais_nasc(rs.getString("pais_nascimento"));
			autor.setBiografia(rs.getString("biografia"));
			
			autores.add(autor);
		}
		rs.close();
		ps.close();
		return autores;
	}

	@Override
	public String inserir(Autor a) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{Call Autor_Procedure(?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "I");
		cs.setInt(2, a.getId_autor());
		cs.setString(3,  a.getNome());
		cs.setString(4, a.getData_nasc().toString());
		cs.setString(5,  a.getPais_nasc());
		cs.setString(6,  a.getBiografia());
		cs.registerOutParameter(6, Types.VARCHAR);
		
		cs.execute();
		String saida = cs.getString(6);
		
		cs.close();
		c.close();
		return saida;
	}

	@Override
	public String atualizar(Autor a) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{Call Autor_Procedure(?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "U");
		cs.setInt(2, a.getId_autor());
		cs.setString(3,  a.getNome());
		cs.setString(4, a.getData_nasc().toString());
		cs.setString(5,  a.getPais_nasc());
		cs.setString(6,  a.getBiografia());
		cs.registerOutParameter(6, Types.VARCHAR);
		
		cs.execute();
		String saida = cs.getString(6);
		
		cs.close();
		c.close();
		return saida;
	}

	@Override
	public String excluir(Autor a) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{Call Autor_Procedure(?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, "D");
		cs.setInt(2, a.getId_autor());
		cs.setNull(3,  Types.VARCHAR);
		cs.setNull(4, Types.VARCHAR);
		cs.setNull(5,  Types.VARCHAR);
		cs.setNull(6,  Types.VARCHAR);
		
		cs.registerOutParameter(6, Types.VARCHAR);
		
		cs.execute();
		String saida = cs.getString(6);
		
		cs.close();
		c.close();
		return saida;
	}
}