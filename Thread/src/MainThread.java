

public class MainThread extends Thread {
	public void run(){		
		try {
			String write = "";
			System.out.println("run");
			main.jButton0.setText("Processing...");
			main.jButton0.setEnabled(false);
			write = Manager.selectNumberEmployee()+"\n"+ Manager.sumSalaryEmployee()+
					"\n"+Manager.avgSalaryEmployee()+"\n"+Manager.employeeHighestSalary()+
					"\n"+Manager.employeeLowestSalary();
			WriteFile.Write(write);
			sleep(500);
			main.jButton0.setText("Process");
			main.jButton0.setEnabled(true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
