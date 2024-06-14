package View;

import Business.BrandManager;
import Business.CarManager;
import Business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.Brand;
import entity.Car;
import entity.Model;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scrl_brand;
    private JTable tbl_brand;
    private JPanel pnl_model;
    private JScrollPane scrll_model;
    private JTable tbl_model;
    private JComboBox cmb_model;
    private JComboBox cmb_model_type;
    private JComboBox cmb_model_fuel;
    private JComboBox cmb_model_gear;
    private JButton btn_search_model;
    private JLabel lbl_brand;
    private JButton btn_cncl_model;
    private JTable tbl_car_brand;
    private JPanel tbl_car;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private DefaultTableModel tmdl_car = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private CarManager carManager;
    private Object[] col_model;
    private Object[] col_car;
    private JPopupMenu brand_menu;
    private JPopupMenu model_menu;
    private JPopupMenu car_menu;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.carManager = new CarManager();
        this.add(container);
        this.guiInitilaze(1000, 500);
        this.user = user;
        if (user == null) { //pencere kapatmak için
            dispose();
        }

        this.lbl_welcome.setText("Hoşgeldiniz " + this.user.getUsername());

        loadBrandTable();
        loadBrandComponent();

        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();

        loadCarTable();
        loadCarComponent();

    }

    private void loadCarComponent() {
        tableRowSelected(this.tbl_car_brand);
        this.car_menu = new JPopupMenu();
        this.car_menu.add("Yeni").addActionListener(e -> {
           CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    loadCarTable();

                }
            });

        });
        this.car_menu.add("Güncelle").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_car_brand, 0);
            CarView carView = new CarView(this.carManager.getById(selectModelId));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    loadCarTable();
                }
            });

        });
        this.car_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectCarId = this.getTableSelectedRow(tbl_car_brand, 0);
                if (this.brandManager.delete(selectCarId)) {
                    Helper.showMessage("done");
                    loadCarTable();

                } else {
                    Helper.showMessage("error");
                }
            }

        });
        this.tbl_car_brand.setComponentPopupMenu(car_menu);

    }



    public void loadBrandComponent() {
        tableRowSelected(this.tbl_brand);
        this.brand_menu = new JPopupMenu();
        this.brand_menu.add("Yeni").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    loadBrandTable();
                    loadModelTable(null);//düzeltmeleri brandde göstermek için ekledik.
                    loadModelFilterBrand();
                }
            });

        });
        this.brand_menu.add("Güncelle").addActionListener(e -> {
            int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
            BrandView brandView = new BrandView(this.brandManager.getById(selectBrandId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                }
            });

        });
        this.brand_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
                if (this.brandManager.delete(selectBrandId)) {
                    Helper.showMessage("done");
                    loadBrandTable();
                    loadModelTable(null);
                    loadModelFilterBrand();
                } else {
                    Helper.showMessage("error");
                }
            }

        });
        this.tbl_brand.setComponentPopupMenu(brand_menu); //sekme açar(yeni,güncelle,sil)
    }

    public void loadBrandTable() {
        Object[] col_brand = {"Marka ID", "Marka Adı"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);
    }

    public void loadCarTable(){
        Object[] col_car = {"ID","Marka","Model","Plaka","Renk","KM","Yıl","Tip","Yakıt Türü","Vites"};
        ArrayList<Object[]> carList = this.carManager.getForTable(col_car.length,this.carManager.findAll());
        this.createTable(this.tmdl_car, this.tbl_car_brand, col_car, carList);
    }

    private void loadModelComponent() {
        tableRowSelected(this.tbl_model);
        this.model_menu = new JPopupMenu();
        this.model_menu.add("Yeni").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) { //yeni ürün ekleyince güncelleme yapması için
                    loadModelTable(null);
                }
            });

        });
        this.model_menu.add("Güncelle").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getById(selectModelId));
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                }
            });

        });
        this.model_menu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectModelId = this.getTableSelectedRow(tbl_model, 0);
                if (this.modelManager.delete(selectModelId)) {
                    Helper.showMessage("done");
                    loadModelTable(null);
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        this.tbl_model.setComponentPopupMenu(model_menu);
        //Arama yap butonu aktifleştirme
        this.btn_search_model.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_model.getSelectedItem();
            int brandId = 0;
            if (selectedBrand != null){
                brandId = selectedBrand.getKey();
            }
            ArrayList<Model> modelListForSearch = this.modelManager.searchForTable(
                    brandId,
                    (Model.Fuel) cmb_model_fuel.getSelectedItem(),
                    (Model.Gear) cmb_model_gear.getSelectedItem(),
                    (Model.Type) cmb_model_type.getSelectedItem()
            );

            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length,modelListForSearch);
            loadModelTable(modelRowListBySearch); // filteleme yaparken tek markanın vs çıkması için
        });
        this.btn_cncl_model.addActionListener(e -> {  //Veriyi tabloda sıfırlamak için
            this.cmb_model_type.setSelectedItem(null);
            this.cmb_model_gear.setSelectedItem(null);
            this.cmb_model_fuel.setSelectedItem(null);
            this.cmb_model.setSelectedItem(null);
            loadModelTable(null); //modelin eski haline getirmek için

        });
    }

    public void loadModelTable(ArrayList<Object[]> modelList) {
        this.col_model =new Object[] {"Model ID", "Marka", "Model Adı", "Tip", "Yıl", "Yakıt Türü", "Vites"};
        if (modelList == null){
            modelList= this.modelManager.getForTable(col_model.length, this.modelManager.findAll());
        }
        createTable(this.tmdl_model, this.tbl_model, col_model, modelList);
    }

    public void loadModelFilter() {
        this.cmb_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_model_type.setSelectedItem(null);
        this.cmb_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_model_gear.setSelectedItem(null);
        this.cmb_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_model_fuel.setSelectedItem(null);
        loadModelFilterBrand();

    }

    public void loadModelFilterBrand() {
        this.cmb_model.removeAllItems();
        for (Brand obj : brandManager.findAll()) {
            this.cmb_model.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_model.setSelectedItem(null);
    }
}
