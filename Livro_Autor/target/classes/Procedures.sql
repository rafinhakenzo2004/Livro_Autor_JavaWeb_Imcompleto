CREATE DATABASE Livro_Autor
GO

USE Livro_Autor
GO

CREATE TABLE Livro (
	codigo	INT				NOT NULL,
	nome	VARCHAR(200)	NOT NULL,
	lingua	VARCHAR(10)		NOT NULL,
	ano		INT				NOT NULL,
PRIMARY KEY (codigo)
)

CREATE TABLE Autor (
	id_Autor	INT			NOT NULL,
	nome		VARCHAR(100)	UNIQUE		NOT NULL,
	data_nasc	DATE			NOT NULL,
	pais_nasc	VARCHAR(50)		NOT NULL,
	biografia	VARCHAR(255)	NOT NULL
	PRIMARY KEY (id_Autor)
)

GO

CREATE OR ALTER PROCEDURE Livro_Procedure
	@nome VARCHAR(200),
	@lingua	VARCHAR(10),
	@ano	INT
AS
BEGIN
	SET NOCOUNT ON

	DECLARE @NovoCodigo INT;

	IF @ano < 1990
	BEGIN
		RAISERROR ('Nao se pode ter livros de ano inferior a 1990', 16, 1);
		RETURN
	END

	IF @lingua IS NULL
	BEGIN
		SET @lingua = 'PT-BR'
	END

	SELECT @NovoCodigo = MAX(codigo) FROM livro
	
	IF @NovoCodigo IS NULL 
		SET @NovoCodigo = 100001
	ELSE	
		SET @NovoCodigo = @NovoCodigo + 100
	
	INSERT INTO Livro (codigo, nome, lingua, ano)
	VALUES (@NovoCodigo, @nome, @lingua, @ano)
	SELECT @NovoCodigo AS CodigoGerado

END
GO

CREATE OR ALTER PROCEDURE Autor_Procedure
	@nome		VARCHAR(100),
	@data_nasc	DATE,
	@pais_nasc	VARCHAR(50),
	@biografia	VARCHAR(255)
AS
BEGIN
	SET NOCOUNT ON 

	IF @pais_nasc NOT IN ('Brasil', 'Inglaterra', 'Estados Unidos', 'Alemanha')
	BEGIN
		RAISERROR ('Erro: Só pode ser registrados no Brasil, Inglaterra, Estados unidos e Alemanha', 16 ,1) 
		RETURN
	END

	IF EXISTS (SELECT 1 FROM Autor WHERE nome = @nome)
	BEGIN
		RAISERROR ('Erro: Esse nome ja existe', 16, 1);
	END 

	DECLARE @novo_id_Autor INT

	IF @novo_id_Autor IS NULL
		SET @novo_id_Autor = 2351
	ELSE
		SET @novo_id_Autor = @novo_id_Autor + 1
	
	INSERT INTO Autor(id_Autor, nome, data_Nasc, pais_nasc, biografia)
	VALUES (@novo_id_Autor, @nome, @data_nasc, @pais_nasc, @biografia)
	SELECT @novo_id_Autor AS idGerado

END
GO