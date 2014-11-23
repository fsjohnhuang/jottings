#interface
接口定义静态字段<br/>
````
public interface Engine{
  String AUTHRO = "fsjohnhuang";
  int VERSION = 1;
}
public class TurboEngineImpl 
  implements Engine{
  public String getAuthorImpl(){
    return TurboEngineImpl.AUTHOR;
  }
  public int getVerImpl(){
    return TurboEngineImpl.VERSION;
  }
  public String getAuthor(){
    return Engine.AUTHOR;
  }
  public int getVer(){
    return Engine.VERSION;
  }
}
````
