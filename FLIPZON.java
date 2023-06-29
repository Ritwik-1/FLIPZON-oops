import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

class Admin{
    private String username;
    private String password;

    private boolean status;

    public Admin(String u,String p){
        this.username=u;
        this.password=p;
    }

    public String getPassword(){
        return this.password;
    }
    public String getUsername(){
        return this.username;
    }

    public int getRandomValue(int Min, int Max){
            return ThreadLocalRandom.current().nextInt(Min, Max + 1);
    }

    public void AddCategory(Application a,String cn,int id){
        category c = new category(cn,id);
        for(category i : a.getCategories()){
            if(i.getCatID() == id){
                System.out.println("Category ID already present!!");
                System.out.println("Please Choose another one:-");
                return;
            }
        }
        a.getCategories().add(c);
    }

    public boolean DeleteCategory(Application a,String cn, int id){
        for(category i : a.getCategories()){
            if(i == null){
                System.out.println("There is no Category added !!");
                System.out.println("Please add one.");
            }
            if(i.getCatID() == id){
                a.getCategories().remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean AddProduct(Application a,float CatId, String ProdName, float prodId, float price,int stock,ArrayList<String> otherSpecs){
        for(category i : a.getCategories()){
            if(i.getCatID() == CatId){
                product p = new product(ProdName,prodId,price,stock,otherSpecs);
                i.getCatProdArray().add(p);
                return true;
            }
        }
        return false;
    }

    public boolean DeleteProduct(Application a,int CatId,float prodId){
        for(category i : a.getCategories()){
            if(i.getCatID() == CatId){
                for(product p : i.getCatProdArray()){
                    if(p == null){
                        return false;
                    }
                    if(p.getProdID() == prodId){
                        i.getCatProdArray().remove(p);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void SetDiscountProd(Application a,float pid,int dN,int dP,int dE){
        int n = (int)pid;
        int f = (int)((pid- n)*10);

        product p =a.getCategories().get((n>0) ? n-1 : 0).getCatProdArray().get((f>0) ? f-1 : 0);

        p.getDiscounts().add(dN);
        p.getDiscounts().add(dP);
        p.getDiscounts().add(dE);
    }

    public void AddGiveAwayDetails(Application a,float pid1,float pid2,float pN,float pP,float pE,String dealID){
        int n1 = (int)pid1;
        int n2 = (int)pid2;

        int f1 = (int)((pid1 - n1)*10);
        int f2 = (int)((pid2 - n2)*10);

        product p1 = a.getCategories().get((n1>0) ? n1-1 : 0).getCatProdArray().get((f1>0) ? f1-1 : 0);
        product p2 = a.getCategories().get((n2>0) ? n2-1 : 0).getCatProdArray().get((f2>0) ? f2-1 : 0);

        Deal d = new Deal(p1,p2,pN,pP,pE,dealID);

        a.getDeals().add(d);
    }

}

interface explore{
    public void explore_Product_Catalogue(Application a);
}

class Customer implements explore{
    private String name;

    private int age;

    private long phone;

    private String email;
    private String password;

    private boolean reg_status;

    private float wallet = 1000;

    private customer_type type;

    private ArrayList<Integer> coupons = new ArrayList<Integer>();

    private ArrayList<product> Cart = new ArrayList<product>();

    private ArrayList<Deal> DealCart = new ArrayList<Deal>();

    public Customer(){}

    public Customer(String n,String p,int age,long ph,String email){
        this.name=n;
        this.age=age;
        this.phone=ph;
        this.email=email;
        this.password=p;

        this.type = new NORMAL();
    }
    public ArrayList<Deal> getDealCart(){
        return this.DealCart;
    }
    public String getName(){return this.name;}
    public int getAge(){return this.age;}
    public long getPhone(){return this.phone;}
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    public boolean getReg_Status(){
        return this.reg_status;
    }

    public ArrayList<product> getCart(){
        return this.Cart;
    }
    public ArrayList<Integer> getCoupons(){
        return this.coupons;
    }

    public void setReg_status(){
        this.reg_status=true;
    }

    public customer_type getType(){
        return this.type;
    }

    public int getRandomValue(int Min, int Max){
        return ThreadLocalRandom.current().nextInt(Min, Max + 1);
    }


    public void upgrade_Status(String n){
        if(n.compareTo("PRIME")==0){
            if(this.wallet<200){
                System.out.println("Sorry! You have insufficient balance");
                System.out.println();
                return;
            }
            this.type = new PRIME();
            this.wallet-=200;
            System.out.println("Status updated to PRIME");
            System.out.println();
        } else if (n.compareTo("ELITE")==0) {
            if(this.wallet<300){
                System.out.println("Sorry! You have insufficient balance");
                System.out.println();
                return;
            }
            this.type = new ELITE();
            this.wallet-=300;
            System.out.println("Status updated to ELITE");
            System.out.println();
        }
    }
    @Override
    public void explore_Product_Catalogue(Application a){
        System.out.println("PRODUCT CATALOGUE : ");
        System.out.println("*******************");
        for(category i : a.getCategories()){
            System.out.println("CATEGORY :"+i.getCatName());
            for(product j : i.getCatProdArray()){
                System.out.println("-->"+j.getProdName());
            }
            System.out.println();
        }
        System.out.println();
    }

    public void add_amount_wallet(float amount){
        this.wallet+=amount;
        System.out.println("Amount added successfully!!");
        System.out.println();
    }

    public void empty_cart(){
        this.getCart().clear();
        this.getDealCart().clear();
        System.out.println("Cart emptied successfully !! ");
        System.out.println();
    }

    public void view_cart(){
        if(this.DealCart.size() !=0){
            System.out.println("Deals present in cart :-");
            for(Deal d : this.DealCart){
                System.out.println("Product 1 : "+d.getDealProducts().get(0).getProdName());
                System.out.println("Product 2 : "+d.getDealProducts().get(1).getProdName());
                System.out.println("Deal Price : "+d.getPrice());
            }
        }
        if(this.Cart.size() == 0){
            System.out.println("Cart is Empty !");
            System.out.println();
            return;
        }
        System.out.println("Items present in cart : ");
        for(product i : this.Cart){
            System.out.println("Product name : "+i.getProdName());
            System.out.println("Product Quantity : "+i.getQuantity());
            System.out.println("Cost = "+i.getPrice()+"*"+i.getQuantity()+"="+(i.getPrice()*i.getQuantity()));
            System.out.println();
        }
        System.out.println();
    }

    public void check_account_balance(){

        System.out.println("Current account balance : Rs."+this.wallet);
        System.out.println();
    }

    public void add_product_cart(Application a, float pid,int quant){
        int n = (int)pid;
        int f = (int)((pid- n)*10);

        if(a.getCategories().size()==0){
            System.out.println("There are no products available.");
            System.out.println();
            return;
        }
        product p =a.getCategories().get((n>0) ? n-1 : 0).getCatProdArray().get((f>0) ? f-1 : 0);

        if(p.getStock() < quant){
            System.out.println("The item is out of stock!!");
            System.out.println("Stock availaible : "+p.getStock()+" pieces");
            System.out.println();
            return;
        }

        p.setQuantity(quant);
        p.setStock(quant);
        this.Cart.add(p);
        System.out.println("Item successfully to the cart");
        System.out.println();
    }

    public void browse_deals(Application a){
        if(a.getDeals().size() == 0){
            System.out.println("There are no current deals availaible.");
            System.out.println();
            return;
        }
        System.out.println("Deals availaible are :");
        int c=1;
        for(Deal d : a.getDeals()){
            System.out.println("Deal"+c+":");
            System.out.println("Deal ID : "+d.getDealID());
            String p1 = d.getDealProducts().get(0).getProdName();
            String p2 = d.getDealProducts().get(1).getProdName();
            if(this.type.getName() == "NORMAL"){
                float p = d.getPrice().get(0);
                System.out.println(p1+ " and "+p2);
                System.out.println("COMBINED PRICE : "+p);
            }
            else if (this.type.getName() == "PRIME"){
                float p = d.getPrice().get(1);
                System.out.println(p1+ " and "+p2);
                System.out.println("COMBINED PRICE : "+p);
            }
            else if (this.type.getName() == "ELITE"){
                float p = d.getPrice().get(2);
                System.out.println(p1+ " and "+p2);
                System.out.println("COMBINED PRICE : "+p);
            }
            c+=1;
        }
        System.out.println();
    }

    public int max2(int a,int b){
        if(a>b){
            return a;
        }
        return b;
    }
    public int max3(int a,int b,int c){
        ArrayList<Integer> A = new ArrayList<Integer>();
        A.add(a);
        A.add(b);
        A.add(c);
        Collections.sort(A);
        int m = A.get(2);
        return m;
    }

    public void checkout_cart(Application a){
        if(this.Cart.size() == 0){
            System.out.println("Cart Empty!!");
            System.out.println("Please add some items");
        }
        System.out.println("Your order is placed successfully.");
        System.out.println("Details :-");
        System.out.println();

        for(product i : this.Cart){
            System.out.println("Product Name : "+i.getProdName());
            System.out.println("Product ID : "+i.getProdID());
            System.out.println("Price : "+i.getPrice());
            System.out.println("Quantity : "+i.getQuantity());
            for(String j : i.getOtherDetails()){
                System.out.println(j);
            }
            System.out.println();
        }

        float total_price = 0;

        if(this.getDealCart().size() !=0){
            float deal_price = 0;
            for(Deal d : this.DealCart){
                if(this.type.getName() == "NORMAL"){
                    deal_price+=d.getPrice().get(0);
                }
                else if (this.type.getName() == "PRIME"){
                    deal_price+=d.getPrice().get(1);
                }
                else if (this.type.getName() == "ELITE") {
                    deal_price+=d.getPrice().get(2);
                }
            }
            System.out.println("Your total price for deals = Rs."+deal_price);
            total_price+=deal_price;
        }

        if(this.type.getName() == "NORMAL"){

            for(product i : this.getCart()){
                if(i.getDiscounts().size() == 0){
                    total_price+=(i.getPrice()*i.getQuantity());
                }
                else{
                    int d = i.getDiscounts().get(0);
                    total_price+= ((i.getPrice()*i.getQuantity()) - (d/100)*(i.getPrice()*i.getQuantity()));
                }
            }

            float del_charge = 100 + (this.type.getDelivery_charge()*(total_price))/100;
            float final_price = total_price + del_charge;
            System.out.println("Total cost = Rs."+final_price);
            System.out.println("Your order will be delivered in "+this.type.getDelivery_time());
            System.out.println();
            this.empty_cart();
        }
        else if(this.type.getName() == "PRIME"){

            int type_discount = this.type.getDiscount_percent();

            int max_coupon = 0;

            if(this.coupons.size() != 0){
                Collections.sort(this.coupons);
                max_coupon = this.coupons.get(this.coupons.size()-1);
            }

            for(product i : this.getCart()){
                if(i.getDiscounts().size() == 0){
                    float m = max2(type_discount,max_coupon);
                    System.out.println("Discount Applicable on product "+i.getProdName()+" is "+m+"%");
                    System.out.println("Applying....");
                    float f = i.getPrice()*i.getQuantity();
                    System.out.println("Total Price of product before discount : "+f);
                    float f3 = m/100;
                    if(f3==0.0){
                        System.out.println("f3 is zero");
                    }
                    float f2 = (f-f*(f3));
                    System.out.println("Price of product after discount : "+f2);
                    total_price+= f2;
                    System.out.println();
                }
                else{
                    float m = max3(i.getDiscounts().get(1),type_discount,max_coupon);
                    System.out.println("Discount Applicable on product :"+i.getProdName()+" is "+m+"%");
                    System.out.println("Applying....");
                    float f = i.getPrice()*i.getQuantity();
                    System.out.println("Total Price of product before discount : "+f);
                    float f3 = m/100;
                    float f2 = (f-f*(f3));
                    System.out.println("Price of product after discount : "+f2);
                    total_price+=f2;
                    System.out.println();
                }
            }

            int dcp = this.type.getDelivery_charge();
            float del_charge = 100 + (dcp*(total_price))/100;

            System.out.println("Delivery charge : Rs."+del_charge);

            System.out.println("Total cost : Rs."+del_charge+total_price);

            System.out.println("Your order will be delivered in "+this.type.getDelivery_time());

            if((del_charge + total_price )>=5000){
                int rand =getRandomValue(1,2);
                if(rand == 1){
                    int d = getRandomValue(5,15);
                    System.out.println("You have won one coupon worth "+d+"% "+"Discount! Congratulations");
                    this.coupons.add(d);
                }
                else{
                    int d1 = getRandomValue(5,15);
                    int d2 = getRandomValue(5,15);
                    System.out.println("You have won two coupons worth "+d1+"% "+"and"+d2+"% "+"Discount! Congratulations");
                    this.coupons.add(d1);
                    this.coupons.add(d2);
                }
            }
            System.out.println();
            this.empty_cart();
        }

        if(this.type.getName() == "ELITE"){

            int type_discount = this.type.getDiscount_percent();

            int max_coupon = 0;

            if(this.coupons.size() != 0){
                Collections.sort(this.coupons);
                max_coupon = this.coupons.get(this.coupons.size()-1);
            }

            for(product i : this.getCart()){
                if(i.getDiscounts().size() == 0){
                    float m = max2(type_discount,max_coupon);
                    System.out.println("Discount Applicable on product "+i.getProdName()+" is "+m+"%");
                    System.out.println("Applying....");
                    float f = i.getPrice()*i.getQuantity();
                    System.out.println("Total Price of product before discount : "+f);
                    float f3 = m/100;
                    float f2 = (f-f*(f3));
                    System.out.println("Price of product after discount : "+f2);
                    total_price+= f2;
                    System.out.println();
                }
                else{
                    float m = max3(i.getDiscounts().get(1),type_discount,max_coupon);
                    System.out.println("Discount Applicable on product :"+i.getProdName()+" is "+m+"%");
                    System.out.println("Applying....");
                    float f = i.getPrice()*i.getQuantity();
                    System.out.println("Total Price of product before discount : "+f);
                    float f3 = m/100;
                    float f2 = (f-f*(f3));
                    System.out.println("Price of product after discount : "+f2);
                    total_price+=f2;
                    System.out.println();
                }
            }

            int dcp = this.type.getDelivery_charge();
            float del_charge = 100 + (dcp*(total_price))/100;
            System.out.println("Delivery charge : Rs."+del_charge);

            System.out.println("Total cost : Rs."+(total_price+del_charge));
            System.out.println("Your order will be delivered in "+this.type.getDelivery_time());

            if((total_price + del_charge) >=5000){
                int rand =getRandomValue(3,4);

                if(rand == 3){
                    int d1 = getRandomValue(5,15);
                    int d2 = getRandomValue(5,15);
                    int d3 = getRandomValue(5,15);
                    System.out.println("You have won 3 coupons worth "+d1+"% "+d2+"% "+" and "+d3+"% "+"Discount! Congratulations");
                    this.coupons.add(d1);
                    this.coupons.add(d2);
                    this.coupons.add(d3);
                }
                else{
                    int d1 = getRandomValue(5,15);
                    int d2 = getRandomValue(5,15);
                    int d3 = getRandomValue(5,15);
                    int d4 = getRandomValue(5,15);
                    System.out.println("You have won 4 coupons worth "+d1+"% "+d2+"% "+d3+"% "+" and "+d4+"% "+"Discount! Congratulations");
                    this.coupons.add(d1);
                    this.coupons.add(d2);
                    this.coupons.add(d3);
                    this.coupons.add(d4);
                }
            }
            this.empty_cart();
        }
    }

    public void view_coupons(){
        if(this.coupons.size() == 0){
            System.out.println("No Coupons availaible");
        }
        else{
            System.out.println("There are "+this.coupons.size()+"coupons availaible!");
            int c=1;
            for(int i : this.coupons){
                System.out.println(c+") "+i+"% "+" Discount");
            }
        }
        System.out.println();
    }

}
interface payable{
    public float calcPayable(float total_price);
}

class customer_type extends Customer implements payable{
    private int discount_percent;
    private String delivery_time;
    private int delivery_charge;

    private String name;

    public customer_type(String name,int dp,String dt,int dcp){
        this.name = name;
        this.discount_percent=dp;
        this.delivery_time=dt;
        this.delivery_charge=dcp;
    }
    public int getDiscount_percent(){
        return this.discount_percent;
    }
    public String getDelivery_time(){
        return this.delivery_time;
    }

    public int getDelivery_charge(){
        return this.delivery_charge;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public float calcPayable(float total_price){
        return 0;
    }

}

class NORMAL extends customer_type{
    private boolean discounts_Coupons=false;

    private String name = "NORMAL";

    public NORMAL(){
        super("NORMAL",0,"7-10 days",5);
    }

    public boolean getDiscounts_Coupons_eligibility(){
        return this.discounts_Coupons;
    }

}

class PRIME extends customer_type{

    private String name = "PRIME";
    private boolean discounts_Coupons = true;
    private String coupons_on_order = "1-2 coupons";

    public PRIME(){
        super("PRIME",5,"3-6 days",2);
    }
    public boolean getDiscounts_Coupons_eligibility(){
        return this.discounts_Coupons;
    }
    public String getCoupons_on_order(){
        return this.coupons_on_order;
    }

}

class ELITE extends customer_type{

    private boolean discounts_Coupons = true;
    private String coupons_on_order = "3-4 coupons";

    private String name = "ELITE";

    public ELITE(){
        super("ELITE",10,"2 days",0);
    }
    public boolean getDiscounts_Coupons_eligibility(){
        return this.discounts_Coupons;
    }
    public String getCoupons_on_order(){
        return this.coupons_on_order;
    }

}


class Application{

    private ArrayList<Admin> reg_admins = new ArrayList<Admin>();
    private ArrayList<Customer> reg_Customers = new ArrayList<Customer>();

    private ArrayList<category> Categories = new ArrayList<category>();

    private ArrayList<Deal> Deals = new ArrayList<Deal>();

    public ArrayList<category> getCategories(){
        return this.Categories;
    }

    public ArrayList<Deal> getDeals(){
        return this.Deals;
    }
    public ArrayList<Admin> getReg_admins(){
        return this.reg_admins;
    }
    public ArrayList<Customer> getReg_Customers(){
        return this.reg_Customers;
    }

}

class category{
    private String name;
    private int catID;
    private ArrayList<product> category_products = new ArrayList<product>();

    public category(){

    }

    public category(String name, int catID){
        this.name=name;
        this.catID=catID;
    }

    public int getCatID(){
        return this.catID;
    }

    public ArrayList<product> getCatProdArray(){
        return this.category_products;
    }

    public String getCatName(){
        return this.name;
    }
}

class product{
    private String name;
    private float prodID;
    private float price;

    // for the customer
    private int quantity;

    // for the admin
    private int stock;

//    Array with discount index 0 for normal , 1 for prime , 2 for elite on the product
    private ArrayList<Integer> discounts = new ArrayList<Integer>();

    private ArrayList<String> OtherDetails = new ArrayList<String>();

    public product(String n,float pID,float price,int stock,ArrayList<String> specs){
        this.name=n;
        this.prodID=pID;
        this.price=price;
        this.OtherDetails=specs;
        this.stock=stock;
    }
    public int getStock(){
        return this.stock;
    }

    public String getProdName(){
        return this.name;
    }

    public float getProdID() {
        return prodID;
    }

    public float getPrice(){
        return this.price;
    }

    public int getQuantity(){
        return this.quantity;
    }
    public void setQuantity(int q){
        this.quantity=q;
    }

    public ArrayList<String> getOtherDetails() {
        return OtherDetails;
    }

    public ArrayList<Integer> getDiscounts(){
        return this.discounts;
    }

    public void setStock(int i) {
        this.stock-=i;
    }
}

class Deal{
      private ArrayList<Float> price = new ArrayList<Float>();
      private String DealID;
    private ArrayList<product> dealProducts = new ArrayList<product>();

    public Deal(product p1,product p2,float pN,float pP,float pE,String DealID){
        this.price.add(pN);
        this.price.add(pP);
        this.price.add(pE);
        this.dealProducts.add(p1);
        this.dealProducts.add(p2);
        this.DealID = DealID;
    }

    public ArrayList<product> getDealProducts(){
        return this.dealProducts;
    }
    public ArrayList<Float> getPrice(){
        return this.price;
    }
    public String getDealID(){return this.DealID;}
}

public class FLIPZON{
    public static void main(String[] args){

        Application App = new Application();

//      Hardcoded admin

        Admin A1 = new Admin("Beff Jezos","Beff");
        System.out.println("WELCOME TO FLIPZON");

        while(true) {
            System.out.println();
            System.out.println("1) Enter as Admin");
            System.out.println("2) Explore the Product Catalog");
            System.out.println("3) Show Available Deals");
            System.out.println("4) Enter as Customer");
            System.out.println("5) Exit the Application");

            System.out.println();

            Scanner sc1 = new Scanner(System.in);
            Scanner sc2 = new Scanner(System.in);

            int c2 = sc1.nextInt();

            if(c2 == 1){
                System.out.println("Dear Admin,");
                System.out.println("Please enter your username and password");
                System.out.println();
                System.out.println("username :-");
                String user = sc2.nextLine();
                System.out.println("password :-");
                String pass = sc2.nextLine();

                if(A1.getUsername().compareTo(user) == 0 && A1.getPassword().compareTo(pass) == 0){

                    System.out.println("Welcome "+user+"!!!!");

                    while(true) {
                        System.out.println("Please choose any one of the following actions:");
                        System.out.println();
                        System.out.println("1) Add category");
                        System.out.println("2) Delete category");
                        System.out.println("3) Add Product");
                        System.out.println("4) Delete Product");
                        System.out.println("5) Set Discount on Product");
                        System.out.println("6) Add giveaway deal");
                        System.out.println("7) Back");

                        int c1 = sc1.nextInt();

                        if(c1 == 1){
                            System.out.println("Add name of the category");
                            System.out.println("Add category ID:-");
                            int catID = sc1.nextInt();

                            boolean flag =false;
                            for(category i : App.getCategories()){
                                if(i.getCatID() == catID){
                                    System.out.println("Category ID already present!!");
                                    System.out.println("Please Choose another one:-");
                                    flag = true;
                                    break;
                                }
                            }
                            if(flag == true){
                                continue;
                            }
                            System.out.println("Add name of the category :-");
                            String catName = sc2.nextLine();

                            A1.AddCategory(App,catName,catID);

                            ArrayList<String> others = new ArrayList<String>();
                            System.out.println("Add a Product :- ");
                            System.out.println("Product name :");
                            String ProdName = sc2.nextLine();
                            System.out.println("Product ID :");
                            float ProdID = sc1.nextFloat();
                            System.out.println("Price in Rs. : ");
                            float Price = sc1.nextFloat();
                            System.out.println("Enter the stock availaible : ");
                            int stock = sc1.nextInt();
                            System.out.println("Enter other details :-");

                            String detail = sc2.nextLine();
                            others.add(detail);

                            while(detail.length() != 0){
                                detail = sc2.nextLine();
                                others.add(detail);
                            }

                            boolean res = A1.AddProduct(App,catID,ProdName,ProdID,Price,stock,others);
                            if(res == true){
                                System.out.println("Product has been added successfully !");
                            }
                            else{
                                System.out.println("Item could not be added");
                            }
                        }
                        else if (c1 == 2) {
                            System.out.println("Enter the category name :-");
                            String catName = sc2.nextLine();
                            System.out.println("Enter the category ID :-");
                            int catID = sc1.nextInt();
                            boolean res = A1.DeleteCategory(App,catName,catID);
                            if(res == true){
                                System.out.println("Category has been deleted successfully !");
                            }
                            else{
                                System.out.println("Category could not be deleted.");
                            }
                        }
                        else if (c1 == 3) {
                                System.out.println("Enter category ID :-");
                                float cID = sc1.nextFloat();
                                ArrayList<String> others = new ArrayList<String>();
                                System.out.println("Add a Product :- ");
                                System.out.println("Product name :");
                                String ProdName = sc2.nextLine();
                                System.out.println("Product ID :");
                                float ProdID = sc1.nextFloat();
                                System.out.println("Price in Rs. : ");
                                float Price = sc1.nextFloat();
                                System.out.println("Enter the stock availaible :");
                                int stock = sc1.nextInt();
                                System.out.println("Enter other details :-");
                                String detail = sc2.nextLine();
                                others.add(detail);

                                while(detail.length() != 0){
                                    detail = sc2.nextLine();
                                    others.add(detail);
                                }

                            boolean res = A1.AddProduct(App,cID,ProdName,ProdID,Price,stock,others);
                                if(res == true){
                                    System.out.println("Product has been added successfully !");
                                }
                                else{
                                    System.out.println("Item could not be added");
                                }

                        }
                        else if (c1 == 4) {
                            System.out.println("Enter the category ID of the product :-");
                            int catID = sc1.nextInt();
                            System.out.println("Enter the product ID :-");
                            float prodID = sc1.nextFloat();

                            boolean res = A1.DeleteProduct(App,catID,prodID);

                            if(res == true){
                                System.out.println("Product has been deleted successfully!");
                            }
                            else{
                                System.out.println("Product could not be deleted.");
                            }
                        }
                        else if (c1 == 5) {
                            System.out.println("Dear Admin give the Product ID you want to add discount for");
                            System.out.println("Enter the Product ID :");
                            float ProdID = sc1.nextFloat();
                            System.out.println("Enter discount for Elite, Prime and Normal customers respectively (in % terms)");
                            System.out.println("For PRIME :-");
                            int prime =sc1.nextInt();
                            System.out.println("For ELITE :-");
                            int elite =sc1.nextInt();
                            System.out.println("For NORMAL :-");
                            int normal =sc1.nextInt();

                            A1.SetDiscountProd(App,ProdID,normal,prime,elite);

                        }
                        else if (c1 == 6){
                            System.out.println("Dear Admin give the Product IDs you want to combine and giveaway a deal for");
                            System.out.println("Enter the first Product ID :");
                            float pid1 = sc1.nextFloat();
                            System.out.println("Enter the second Product ID:");
                            float pid2 = sc1.nextFloat();
                            System.out.println("Enter the deal ID :");
                            String did = sc2.nextLine();
                            System.out.println("Enter the combined price(Should be less than their combined price):- ");
                            System.out.println("Enter price for NORMAL customers :-");
                            float pN = sc1.nextFloat();
                            System.out.println("Enter price for PRIME customers :-");
                            float pP = sc1.nextFloat();
                            System.out.println("Enter price for ELITE customers :-");
                            float pE = sc1.nextFloat();

                            A1.AddGiveAwayDetails(App,pid1,pid2,pN,pP,pE,did);
                        }
                        else {
                            break;
                        }
                    }

                }
                else{
                    System.out.println("Wrong credentials !!");
                    System.out.println("Please try again");
                }
            } else if (c2 == 2) {
                System.out.println("Product catalogue :-");
                for(category i : App.getCategories()){
                    System.out.println();
                    System.out.println("Category :"+i.getCatName());
                    System.out.println("Category ID : "+i.getCatID());
                    System.out.println("Products availaible :-");
                    for(product j : i.getCatProdArray()){
                        System.out.println("Product name :"+j.getProdName());
                        System.out.println("Product ID : "+j.getProdID());
                        System.out.println("Price : "+j.getPrice());
                        System.out.println("Stock availaible : "+j.getStock());
                        System.out.println("other details :-");
                        for(String k : j.getOtherDetails()){
                            System.out.println(k);
                        }
                    }
                }
                System.out.println();
            } else if (c2 == 3) {
                if(App.getDeals().size() == 0){
                    System.out.println("Dear User, there are no deals for now!!! Please check regularly for exciting deals.");
                }
                else{
                    System.out.println("Deals availaible :-");
                    System.out.println();
                    for(Deal i : App.getDeals()){
                        System.out.println("Products : "+i.getDealProducts().get(0).getProdName());
                        System.out.println("           "+ i.getDealProducts().get(1).getProdName());
                        System.out.println("Combined price for NORMAL customers : "+i.getPrice().get(0));
                        System.out.println("Combined price for PRIME customers : "+i.getPrice().get(1));
                        System.out.println("Combined price for ELITE customers : "+i.getPrice().get(2));
                        System.out.println();
                    }
                }
            }
            else if (c2 == 4) {
                while(true){
                    System.out.println("1) Sign up");
                    System.out.println("2) Log in");
                    System.out.println("3) Back ");

                    int c3 = sc1.nextInt();

                    if(c3== 1){
                        System.out.println("Enter username :-");
                        String name = sc2.nextLine();
                        System.out.println("Enter password :-");
                        String password = sc2.nextLine();
                        System.out.println("Enter your age :-");
                        int age = sc1.nextInt();
                        System.out.println("Enter your phone number :-");
                        long ph = sc1.nextLong();
                        System.out.println("Enter your email ID :-");
                        String email = sc2.nextLine();

                        Customer c = new Customer(name,password,age,ph,email);

                        App.getReg_Customers().add(c);
                        c.setReg_status();
                        System.out.println("customer successfully registered!!");
                    }
                    else if (c3 == 2) {
                        System.out.println("Enter name :-");
                        String name = sc2.nextLine();
                        System.out.println("Enter password :-");
                        String pass = sc2.nextLine();

                        int a =0;
                        for(Customer i : App.getReg_Customers()){
                            if(i.getName().compareTo(name) == 0 && i.getPassword().compareTo(pass) == 0 && i.getReg_Status() == true){
                                a=1;
                                System.out.println("Welcome "+i.getName()+"!!");

                                while(true){
                                    System.out.println("1) browse products ");
                                    System.out.println("2) browse deals    ");
                                    System.out.println("3) add a product to cart");
                                    System.out.println("4) add products in deal to cart");
                                    System.out.println("5) view coupons ");
                                    System.out.println("6) check account balance ");
                                    System.out.println("7) view cart");
                                    System.out.println("8) empty cart");
                                    System.out.println("9) checkout cart");
                                    System.out.println("10) upgrade customer status");
                                    System.out.println("11) Add amount to wallet");
                                    System.out.println("12) back");

                                    int c4 = sc1.nextInt();

                                    if(c4 == 1){
                                        i.explore_Product_Catalogue(App);
                                    }
                                    else if (c4 == 2) {
                                        i.browse_deals(App);
                                    }
                                    else if (c4 == 3) {
                                        System.out.println("Enter product ID :-");
                                        float prodID = sc1.nextFloat();
                                        System.out.println("Enter quantity :-");
                                        int quant = sc1.nextInt();
                                        i.add_product_cart(App,prodID,quant);
                                    }
                                    else if (c4 == 4) {
                                        System.out.println("Availaible deals :-");
                                        i.browse_deals(App);
                                        if(App.getDeals().size() == 0){
                                            continue;
                                        }
                                        System.out.println();
                                        System.out.println("Enter the deal ID you want to add into cart (Eg. D1 or D2 :- ");
                                        String did = sc2.nextLine();
                                        for(Deal d : App.getDeals()){
                                            if(d.getDealID() == did){
                                                i.getDealCart().add(d);
                                            }
                                        }
                                    }
                                    else if (c4 == 5){
                                        i.view_coupons();
                                    }
                                    else if (c4 == 6){
                                        i.check_account_balance();
                                    }
                                    else if (c4 == 7){
                                        i.view_cart();
                                    }
                                    else if (c4 == 8){
                                        i.empty_cart();
                                    }
                                    else if (c4 == 9) {
                                        i.checkout_cart(App);
                                    }
                                    else if (c4 == 10) {
                                        System.out.println("Current status :"+i.getType().getName());
                                        System.out.println("Choose new status :- ");
                                        String sta = sc2.nextLine();
                                        i.upgrade_Status(sta);
                                    }
                                    else if (c4 == 11) {
                                        System.out.println("Enter amount to add :-");
                                        float amount = sc1.nextFloat();
                                        i.add_amount_wallet(amount);
                                    }
                                    else{
                                        break;
                                    }

                                }
                            }
                        }
                        if(a == 0){
                            System.out.println("User not registered !!");
                        }
                    }
                    else{
                        break;
                    }
                }
            }
            else{
                break;
            }
        }
    }
}










