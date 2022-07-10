package sp.misc;

import java.lang.reflect.Constructor;

public class RefMain {
    /*
	URL classUrl;
	    classUrl = new URL("file:./path/"); // .class를 포함하고 있는 상위 경로
	    URL[] classUrls = { classUrl };
	    URLClassLoader ucl = new URLClassLoader(classUrls);
	    Class<?> c = ucl.loadClass("aaa.bbb.MyClassA");
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void run() {
        try {
            Class clazz = Class.forName("programmers.lv1.Q12903");
            System.out.println("Class name: "+clazz.getName());

            // 모든 생성자
            Constructor constructors[] = clazz.getDeclaredConstructors();
            for (Constructor cons : constructors) {
                System.out.println("Get constructors in Child: " + cons);
            }
            System.out.println();
            /*
            // 객체 생성
            Constructor constructor = clazz.getConstructor();
            Q12903 obj = (Q12903)constructor.newInstance();
            // public 생성자
            Constructor constructors2[] = clazz.getConstructors();
            for (Constructor cons : constructors2) {
                System.out.println("Get public constructors in Child: " + cons);
            }
            System.out.println();
            // 모든 메소드 (Super class 제외)
            Method methods[] = clazz.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("Get methods in Child: " + method);
            }
            System.out.println();
            // public 메소드 (상속받은 메소드 포함)
            Method methods2[] = clazz.getMethods();
            for (Method method : methods2) {
                System.out.println("Get public methods in both Parent and Child: " + method);
            }
            System.out.println();
            // 메소드 호출
            Method method = clazz.getDeclaredMethod("solution", String.class);
            String ret = (String)method.invoke(obj, "abcde"); // static일 경우 첫번째 인자 null
            System.out.println(ret);
             */
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            //        } catch (NoSuchMethodException e) {
            //            // TODO Auto-generated catch block
            //            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RefMain m = new RefMain();
        m.run();
    }
}
