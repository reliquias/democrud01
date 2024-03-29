USE [master]
GO
/****** Object:  Database [spring01]    Script Date: 1/11/2022 9:52:50 PM ******/
CREATE DATABASE [spring01]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'spring01', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\spring01.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'spring01_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL11.MSSQLSERVER\MSSQL\DATA\spring01_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [spring01] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [spring01].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [spring01] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [spring01] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [spring01] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [spring01] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [spring01] SET ARITHABORT OFF 
GO
ALTER DATABASE [spring01] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [spring01] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [spring01] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [spring01] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [spring01] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [spring01] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [spring01] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [spring01] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [spring01] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [spring01] SET  DISABLE_BROKER 
GO
ALTER DATABASE [spring01] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [spring01] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [spring01] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [spring01] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [spring01] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [spring01] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [spring01] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [spring01] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [spring01] SET  MULTI_USER 
GO
ALTER DATABASE [spring01] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [spring01] SET DB_CHAINING OFF 
GO
ALTER DATABASE [spring01] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [spring01] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [spring01]
GO
/****** Object:  Table [dbo].[contact]    Script Date: 1/11/2022 9:52:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[contact](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[email] [varchar](255) NULL,
	[name] [varchar](255) NULL,
	[phone] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_sistem]    Script Date: 1/11/2022 9:52:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_sistem](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[email] [varchar](255) NULL,
	[login] [varchar](255) NULL,
	[name] [varchar](255) NULL,
	[password] [varchar](255) NULL,
	[phone] [varchar](255) NULL,
	[role_user] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[contact] ON 

INSERT [dbo].[contact] ([id], [email], [name], [phone]) VALUES (CAST(10033 AS Numeric(19, 0)), N'jaque.marques10@yahoo.com.br', N'Jaqueline da Silva Marques', N'11988834468')
INSERT [dbo].[contact] ([id], [email], [name], [phone]) VALUES (CAST(10044 AS Numeric(19, 0)), N'jjjjj@ferreira.com', N'Juliete', N'11987889898')
INSERT [dbo].[contact] ([id], [email], [name], [phone]) VALUES (CAST(10046 AS Numeric(19, 0)), N'sebastian@soldado.com', N'Sebastião', N'11999248978')
INSERT [dbo].[contact] ([id], [email], [name], [phone]) VALUES (CAST(10048 AS Numeric(19, 0)), N'tiana@soldado.com', N'Tiana', N'11999248978')
INSERT [dbo].[contact] ([id], [email], [name], [phone]) VALUES (CAST(10050 AS Numeric(19, 0)), N'ap@cida.com', N'Aparecida', N'11854787878')
SET IDENTITY_INSERT [dbo].[contact] OFF
SET IDENTITY_INSERT [dbo].[user_sistem] ON 

INSERT [dbo].[user_sistem] ([id], [email], [login], [name], [password], [phone], [role_user]) VALUES (CAST(6 AS Numeric(19, 0)), N'truereliquias@yahoo.com.br', NULL, N'Wanderson', N'$2a$12$6oYBcDmfHOAhrewnRZuY3emZ1nDvsYeQmEJY.QRQu9qIyctx6.qHu', N'11992471188', 0)
INSERT [dbo].[user_sistem] ([id], [email], [login], [name], [password], [phone], [role_user]) VALUES (CAST(7 AS Numeric(19, 0)), N'wreliquias@gmail.com', NULL, N'wreliquias', N'$2a$12$DZG.l3zdr3dK4.DjSflXNukHhK9QaaGBkR1vDw7k4ACDoh/YMiFfq', N'11992471188', 1)
SET IDENTITY_INSERT [dbo].[user_sistem] OFF
USE [master]
GO
ALTER DATABASE [spring01] SET  READ_WRITE 
GO
