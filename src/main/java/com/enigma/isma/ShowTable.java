package com.enigma.isma;

import com.enigma.isma.entity.*;

import java.util.Date;


public class ShowTable {
    public static void printUser(User user, Integer numb){
        System.out.println("No.\t\t: " + numb);
        System.out.println("ID Member\t: " + user.getId());
        System.out.println("Nama\t\t: " + user.getName());
        System.out.println("Email\t\t: " + user.getEmail());
        System.out.println("No.HP\t\t: " + user.getPhone());
        System.out.println("Alamat\t\t: " + user.getAddress());
        System.out.println("Role\t\t: " + user.getRole());
        System.out.println("=========================================");
    }

    public static void printBook(Books book, Integer numb){
        System.out.println("No.\t\t\t: " + numb);
        System.out.println("Judul\t\t:" + book.getTitle());
        System.out.println("Penulis\t\t:" + book.getAuthor());
        System.out.println("Kategori\t: " + book.getCategory().getName());
        System.out.println("Durasi\t\t: " +book.getCategory().getDuration()+" hari");
        System.out.println("Harga\t\t: " + book.getCategory().getFeeBook());
        System.out.println("Kode Rak\t: " + book.getShelf().getShelfCode());
        String status;
        if (book.isAvailable()){
            status="tersedia";
        }else{
            status="dipinjam";
        }
        System.out.println("Status\t\t: " + status);
        System.out.println("=================================================");
    }

    public static void printBorrowedBookOrderByUser(Transaction transaction, Integer numb, User user){
        Books book = transaction.getBookTransaction();

        if (!book.isAvailable()){
        if (user==transaction.getMember()){
            System.out.println("No.\t\t\t: " + numb);
            System.out.println("Judul\t\t:" + book.getTitle());
            System.out.println("Penulis\t\t:" + book.getAuthor());
            System.out.println("Kategori\t: " + book.getCategory().getName());
            System.out.println("Durasi\t\t: " +book.getCategory().getDuration()+" hari");
            System.out.println("Harga\t\t: " + book.getCategory().getFeeBook());
            System.out.println("Kode Rak\t: " + book.getShelf().getShelfCode());
            String status="dipinjam";
            System.out.println("Status\t\t: " + status);
            System.out.println("=================================================");
        }
        }else {
            System.out.println("Tidak ada Buku yang masih dipinjam");
        }
    }


    public static void printBookWithTrx(Transaction transaction, Integer numb){
        Books book = transaction.getBookTransaction();

        Long difdate = new Date().getTime() - transaction.getRentDate().getTime();
        Long difDay = difdate / (24 * 60 * 60 * 1000);
        Double penaltyfee=0.0;
        if (difDay > transaction.getBookTransaction().getCategory().getDuration()) {
            int difference = (int) (difDay - transaction.getBookTransaction().getCategory().getDuration());
            penaltyfee = difference * (transaction.getPayment() * 0.05);
        }
        if (!book.isAvailable()){
            System.out.println("No.\t\t\t: " + numb);
            System.out.println("Judul\t\t:" + book.getTitle());
            System.out.println("Penulis\t\t:" + book.getAuthor());
            System.out.println("Kategori\t: " + book.getCategory().getName());
            System.out.println("Durasi\t\t: " +book.getCategory().getDuration()+" hari");
            System.out.println("Harga\t\t: " + book.getCategory().getFeeBook());
            System.out.println("Kode Rak\t: " + book.getShelf().getShelfCode());
            System.out.println("Estimasi Denda\t: "+ penaltyfee);
            String status="dipinjam";
            System.out.println("Status\t\t: " + status);
            System.out.println("=================================================");
        }else {
            System.out.println("Tidak ada Buku yang masih dipinjam");
        }
    }

    public static void printBookWithTrxOrderByUser(Transaction transaction, Integer numb,User user){
        if (user==transaction.getMember()){
            Books book = transaction.getBookTransaction();
            Long difdate = new Date().getTime() - transaction.getRentDate().getTime();
            Long difDay = difdate / (24 * 60 * 60 * 1000);
            Double penaltyfee=0.0;
            if (difDay > transaction.getBookTransaction().getCategory().getDuration()) {
                int difference = (int) (difDay - transaction.getBookTransaction().getCategory().getDuration());
                penaltyfee = difference * (transaction.getPayment() * 0.05);
            }
            if (!book.isAvailable()){
                System.out.println("No.\t\t\t: " + numb);
                System.out.println("Judul\t\t:" + book.getTitle());
                System.out.println("Penulis\t\t:" + book.getAuthor());
                System.out.println("Kategori\t: " + book.getCategory().getName());
                System.out.println("Durasi\t\t: " +book.getCategory().getDuration()+" hari");
                System.out.println("Harga\t\t: " + book.getCategory().getFeeBook());
                System.out.println("Kode Rak\t: " + book.getShelf().getShelfCode());
                System.out.println("Estimasi Denda\t: "+ penaltyfee);
                String status="dipinjam";
                System.out.println("Status\t\t: " + status);
                System.out.println("=================================================");
            }
        }else {
            System.out.println("Tidak ada Buku yang masih dipinjam");
        }
    }
    public static void printCategory(Category category, Integer numb){
        System.out.println("No\t\t\t\t: " + numb);
        System.out.println("ID Category\t\t: " + category.getId());
        System.out.println("Kategori buku\t: " + category.getName());
        System.out.println("harga\t\t\t: " + category.getFeeBook());
        System.out.println("Batas pinjam\t: "+category.getDuration()+" hari");
        System.out.println("=================================================");

    }
    public static void printTransaction(Transaction transaction, Integer numb){
        System.out.println("No\t\t: " + numb);
        System.out.println("ID Transaksi\t: " + transaction.getId());
        System.out.println("Judul Buku\t: " + transaction.getBookTransaction().getTitle());
        System.out.println("Nama Member\t: " + transaction.getMember().getName());
        System.out.println("Tanggal Pinjam\t: "+transaction.getRentDate());
        System.out.println("Tanggal Kembali\t: "+ transaction.getReturnDate());
        System.out.println("Harga sewa\t: " + transaction.getPayment());
        System.out.println("Batas waktu sewa : " + transaction.getBookTransaction().getCategory().getDuration());
        System.out.println("Denda\t\t: " + transaction.getPenaltyfee());
        System.out.println("=========================================");

    }
}
