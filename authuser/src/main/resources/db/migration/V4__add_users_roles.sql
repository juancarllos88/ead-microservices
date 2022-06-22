insert into tb_users_roles select (select id from tb_users where user_name = 'admin.ead') , (select id from tb_roles where name = 'ROLE_ADMIN') ;

insert into tb_users_roles select (select id from tb_users where user_name = 'instrutor.ead') , (select id from tb_roles where name = 'ROLE_INSTRUCTOR') ;

insert into tb_users_roles select (select id from tb_users where user_name = 'juan.carlos') , (select id from tb_roles where name = 'ROLE_STUDENT') ;
insert into tb_users_roles select (select id from tb_users where user_name = 'suelen.barros') , (select id from tb_roles where name = 'ROLE_STUDENT') ;
insert into tb_users_roles select (select id from tb_users where user_name = 'suenia.barros') , (select id from tb_roles where name = 'ROLE_STUDENT') ;
insert into tb_users_roles select (select id from tb_users where user_name = 'rayra.mayrla') , (select id from tb_roles where name = 'ROLE_STUDENT') ;
insert into tb_users_roles select (select id from tb_users where user_name = 'thiago.araujo') , (select id from tb_roles where name = 'ROLE_STUDENT') ;
insert into tb_users_roles select (select id from tb_users where user_name = 'maria.silva') , (select id from tb_roles where name = 'ROLE_STUDENT') ;

