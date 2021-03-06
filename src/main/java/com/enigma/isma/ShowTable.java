package com.enigma.isma;

import com.enigma.isma.entity.*;

import java.util.Date;


public class ShowTable {

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
        if (transaction!=null){
            if (user.getId().equals(transaction.getMember().getId())){
                Books book = transaction.getBookTransaction();
                if (!book.isAvailable()){
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
            } else {
                System.out.println("Kamu tidak memiliki buku yang masih dipinjam");
            }
        }else {
            System.out.println("Kamu tidak memiliki buku yang masih dipinjam");
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
        if (user.getId().equals(transaction.getMember().getId())){
            Books book = transaction.getBookTransaction();
           if (book!=null){
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
        System.out.println("No\t\t\t\t: " + numb);
        System.out.println("ID Transaksi\t: " + transaction.getId());
        System.out.println("Judul Buku\t\t: " + transaction.getBookTransaction().getTitle());
        System.out.println("Nama Member\t\t: " + transaction.getMember().getName());
        System.out.println("Tanggal Pinjam\t: "+transaction.getRentDate());
        System.out.println("Tanggal Kembali\t: "+ transaction.getReturnDate());
        System.out.println("Harga sewa\t\t: " + transaction.getPayment());
        System.out.println("Batas waktu sewa : " + transaction.getBookTransaction().getCategory().getDuration());
        System.out.println("Denda\t\t\t: " + transaction.getPenaltyfee());
        System.out.println("=========================================");
    }

    public static void printTransactionOrderByUser(Transaction transaction, Integer numb,User user){
      if (transaction!=null){
          if (user.getId().equals(transaction.getMember().getId())){
              System.out.println("No\t\t\t\t: " + numb);
              System.out.println("ID Transaksi\t: " + transaction.getId());
              System.out.println("Judul Buku\t\t: " + transaction.getBookTransaction().getTitle());
              System.out.println("Tanggal Pinjam\t: "+transaction.getRentDate());
              System.out.println("Tanggal Kembali\t: "+ transaction.getReturnDate());
              System.out.println("Harga sewa\t\t: " + transaction.getPayment());
              System.out.println("Batas waktu sewa : " + transaction.getBookTransaction().getCategory().getDuration());
              System.out.println("Denda\t\t\t: " + transaction.getPenaltyfee());
              System.out.println("=========================================");

          }else {
              System.out.println("Kamu tidak memiliki transaksi");
          }
      }
    }
}
