SET IDENTITY_INSERT [dbo].[contact] ON 

INSERT [dbo].[contact] ([id], [email], [name], [phone]) VALUES (CAST(10033 AS Numeric(19, 0)), N'jaque.marques10@yahoo.com.br', N'Jaqueline da Silva Marques', N'11988834468')
INSERT [dbo].[contact] ([id], [email], [name], [phone]) VALUES (CAST(10044 AS Numeric(19, 0)), N'jjjjj@ferreira.com', N'Juliete', N'11987889898')
INSERT [dbo].[contact] ([id], [email], [name], [phone]) VALUES (CAST(10046 AS Numeric(19, 0)), N'sebastian@soldado.com', N'Sebastião', N'11999248978')
INSERT [dbo].[contact] ([id], [email], [name], [phone]) VALUES (CAST(10048 AS Numeric(19, 0)), N'tiana@soldado.com', N'Tiana', N'11999248978')
INSERT [dbo].[contact] ([id], [email], [name], [phone]) VALUES (CAST(10050 AS Numeric(19, 0)), N'ap@cida.com', N'Aparecida', N'11854787878')
SET IDENTITY_INSERT [dbo].[contact] OFF
SET IDENTITY_INSERT [dbo].[user_sistem] ON 

INSERT [dbo].[user_sistem] ([id], [email], [name], [password], [phone], [role_user]) VALUES (CAST(6 AS Numeric(19, 0)), N'truereliquias@yahoo.com.br', N'Wanderson', N'$2a$12$6oYBcDmfHOAhrewnRZuY3emZ1nDvsYeQmEJY.QRQu9qIyctx6.qHu', N'11992471188', 0)
INSERT [dbo].[user_sistem] ([id], [email], [name], [password], [phone], [role_user]) VALUES (CAST(7 AS Numeric(19, 0)), N'wreliquias@gmail.com', N'wreliquias', N'$2a$12$DZG.l3zdr3dK4.DjSflXNukHhK9QaaGBkR1vDw7k4ACDoh/YMiFfq', N'11992471188', 1)
SET IDENTITY_INSERT [dbo].[user_sistem] OFF
