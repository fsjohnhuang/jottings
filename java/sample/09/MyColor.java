public enum MyColor{
  RED, BLUE(){
     @Override
     public boolean getFlag(){
       return false;
     }
  };
  public boolean getFlag(){
    return true;
  }
}
