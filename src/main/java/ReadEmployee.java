import com.xyzcorp.Employee;

class ReadEmployee {
 public static void main(String[] args) {
     Employee employee = new Employee("Bob", "Foo", scala.Some.apply(30));
     System.out.println(employee);
 }  
}
