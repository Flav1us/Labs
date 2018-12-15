1. Собранный дистрибутив находится в папке  .\build\distribution\testcms.war
2. Приложение тестировалось на Java 1.5.0_02, Tomcat 5.5 в браузерах Internet Explorer 6.0 и Mozilla FierFox 1.07
3. Для компиляции необходимо в файле bild.xml устанивить пути в библиотеки Tomcat:
   <property name="tomcat.lib.dir" value="C:\Tomcat_5.5\common\lib"/>
   
4. Замечания к приложению
  4.1 Для входа в приложение используются два логина, прописанных в  UserDao.java:
      petrov/password и ivanov/password
  4.2 Для проверки работы сортировки по дате можно в файле SiteDao.java раскоментировать блок static в 
      SiteDao.java и пересобрать дистрибутив. 
  4.3 Для выбора сайта в списке необходимо кликнуть по соответствующей строке.
    