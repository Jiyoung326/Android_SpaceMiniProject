package kr.or.womanup.nambu.hjy.spaceminiproject;

public class Mars {
    int id;
    String sol;
    String cameraName;
    String img_src;
    String earth_date;
    String rover_name;
    String landing_date;
    String launch_date;
    String status;

    public Mars(String rover_name, String cameraName, String img_src,String earth_date) {
        this.earth_date = earth_date;
        this.cameraName = cameraName;
        this.img_src = img_src;
        this.rover_name = rover_name;
    }

    public Mars(int id, String sol, String cameraName, String img_src, String earth_date,
                String rover_name, String landing_date, String launch_date, String status) {
        this.id = id;
        this.sol = sol;
        this.cameraName = cameraName;
        this.img_src = img_src;
        this.earth_date = earth_date;
        this.rover_name = rover_name;
        this.landing_date = landing_date;
        this.launch_date = launch_date;
        this.status = status;
    }
}
