## 建立主键
1. **自增主键**<br/>
````
CREATE TABLE tmp(
  id integer primary key autoincrement
);
````
2. **联合主键**<br/>
````
CREATE TABLE tmp(
  name text,
  num text,
  primary key (name, num)
);
````
