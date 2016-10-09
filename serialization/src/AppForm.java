import java.awt.*;
import java.util.*;
import java.io.*;

public class AppForm extends Frame{ //Форма основного класса

  ObjectOutputStream oos;//Объектная переменная для записи объектов в файл
  FileOutputStream fos;//Переменная для чтения объектов из файла
  ObjectInputStream ois;
  FileInputStream fis;
  static AppForm form;
  Button bexit=new Button("Exit");//Кнопка для выхода
  Button b_serialize=new Button("Serial");//Кнопка для сериализации
  Button b_deserialize=new Button("Deserialize");//Кнопка для десериализации
  TextField tf=new TextField("100");

  AppForm(String s){ //Конструктор основной формы
    super(s);
    setLayout(null);
    add(bexit);
    add(b_serialize);
    add(b_deserialize);
    add(tf);
    setBackground(new Color(100,20,150));
    bexit.setBounds(10,20,60,20);
    b_serialize.setBounds(10,40,100,20);
    b_deserialize.setBounds(10,60,100,20);
    tf.setBounds(10,100,200,20);
  }
  public boolean action(Event evt, Object ob) {//Обработчик событий
    if (evt.target==bexit)//Выход из приложения
    {System.exit(0);
      return true; }
    else
      if (evt.target==b_deserialize) {//Запуск десериализации

        File  fl=new File("tmpserial");//Проверяем, есть ли файл с записанными объектами
        if (fl.exists())
        {tf.setText("FileExists");
          DialogBox d= new DialogBox();//Создаем новую форму на базе класса DialogBox
          try{ 
            fis= new FileInputStream("tmpserial");//Создание потоковой переменной для низкоуровневого ввода
            ois=new ObjectInputStream(fis);//Создание потоковой переменной для высокоуровневого ввода
            d.bexitD= (Button) ois.readObject();  //Читаем на новую форму объекты. При чтении выполняем приведение типа
            d.bexitD.setBounds(10,30,100,20);
            d.add(d.bexitD);
            d.b_serializeD=(Button) ois.readObject();
            d.b_serializeD.setBounds(10,50,100,20);
            d.add(d.b_serializeD); 
            d.b_deserializeD=(Button) ois.readObject();
            d.b_deserializeD.setBounds(10,70,100,20);
            d.add(d.b_deserializeD); 
            d.tfD=(TextField) ois.readObject();
            d.tfD.setBounds(10,90,100,20);
            d.add(d.tfD); 
            d.show();//Отображаем новую форму
            fis.close();//Закрываем поток для ввода
            return true;
          }
          catch(Exception err)
          {System.out.println("Error:"+err);}
        } else
        {tf.setText("FileNitExists");}
        return true;}
      else
        if (evt.target==b_serialize)//Активировано событие сериализации
        {
          try{ 
            fos= new FileOutputStream("tmpserial");
            oos=new ObjectOutputStream(fos);//Запись в файл кнопок и текстового поля
            oos.writeObject(bexit);  
            oos.writeObject(b_serialize);
            oos.writeObject(b_deserialize);
            oos.writeObject(tf);
            fos.close();
            return true;
          }
          catch(Exception err)
          {System.out.println("Error:"+err);}
        }
    return false;
  }
  public static void main() {    
    form = new AppForm("Serialization");
    form.resize(400,400);
    form.move(200,100);
    form.show();  
  }
}
