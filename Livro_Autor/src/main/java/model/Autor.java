package model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autor{
	
	private int id_autor;
	private String nome;
	private LocalDate data_nasc;
	private String pais_nasc;
	private String biografia;
}
