import java.awt.*;
import java.util.*;
import java.io.*;

class DialogBox extends Frame{//класс на основе формы для десериализации
  FileInputStream fis;//Объявление файловой переменной для чтения
  FileOutputStream fos;//Объявление файловой переменной для записи
  ObjectInputStream ois;//ois используется для чтения объектов
  ObjectOutputStream oos;//oos используется для записи объектов
  Button bexitD;//Кнопка для выхода
  Button b_serializeD;//кнопка для сериадизации
  Button b_deserializeD;//кнопка для десериализации
  TextField tfD;//текстовое поле
  DialogBox()//Конструктор десериализуемой формы
  {
    setBackground(new Color(180,100,150));
    setLayout(null);
    resize(400,500);
    move(50,100);
  }

  public boolean action(Event evt, Object ob)
  { if (evt.target==bexitD)
    {this.dispose();
      return true; }
    else
      if (evt.target==b_deserializeD)
      { 
        File  fl=new File("secret");//выполняется 
        //десериализация из файла secret
        if (fl.exists())//проверяем, существует ли этот файл
        {tfD.setText("FileExists");
          DialogBox dialog= new DialogBox();//Создаем новую форму
          try{ 
            fis= new FileInputStream("secret");
            ois=new ObjectInputStream(fis);
            dialog.bexitD= (Button) ois.readObject();  //Читаем из 
            //файла кнопку и выполняем приведение типа
            dialog.bexitD.setBounds(10,30,100,20);//Устанавливаем 
            //ее размеры и координаты
            dialog.add(dialog.bexitD);//Добавляем кнопку на форму
            dialog.b_serializeD=(Button) ois.readObject();//Те же 
            //действия выполняем со второй кнопкой
            dialog.b_serializeD.setBounds(10,50,100,20);
            dialog.add(dialog.b_serializeD); 
            dialog.b_deserializeD=(Button) ois.readObject();
            dialog.b_deserializeD.setBounds(10,70,100,20);
            dialog.add(dialog.b_deserializeD); 
            dialog.tfD=(TextField) ois.readObject();//Читаем из фала текстовое поле  и выполняем приведение типа
            dialog.tfD.setBounds(10,90,100,20);
            dialog.add(dialog.tfD); 
            dialog.show();// Отображаем десериализованную форму
            fis.close();
            return true;
          }
          catch(Exception err)
          {System.out.println("Error:"+err);}
        } else
        {tfD.setText("FileNotExists");}
        return true;}
      else
        if (evt.target==b_serializeD) //Кнопка для сериализации
        {
          try{ 
            fos= new FileOutputStream("secret");//Создаем файл для 
            //сериализации
            oos=new ObjectOutputStream(fos);//Создаем потоковую переменную для
            //записи в файл объектов
            oos.writeObject(bexitD);  //Пишем в файл кнопки и текстовое поле
            oos.writeObject(b_serializeD);
            oos.writeObject(b_deserializeD);
            oos.writeObject(tfD);
            fos.close();
            return true;
          }
          catch(Exception err)
          {System.out.println("Error:"+err);}
        }
    return false;
  }
}
