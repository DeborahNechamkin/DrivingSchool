import java.time.LocalDate;

public class CarRepair {
 private int RepairID;
 private String VIN;
 private LocalDate date;
 private CarRepairType repairType;
 
 public CarRepair(int id,String vin,LocalDate date,CarRepairType type){
	 this.RepairID=id;
	 this.VIN=vin;
	 this.date=date;
	 this.repairType=type;
 }
 
 public CarRepairType getRepairType(){
	 return repairType;
	 
 }
 public LocalDate getDate(){
	 return date;
 }
}
