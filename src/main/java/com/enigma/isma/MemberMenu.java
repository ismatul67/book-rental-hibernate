package com.enigma.isma;

import com.enigma.isma.dao.*;
import com.enigma.isma.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MemberMenu {
    public void printMenu(BufferedReader br, BooksDao booksDao, TransactionDao transactionDao, ShelfDao shelfDao, CategoryDao categoryDao, User user) throws Exception {
		Category category;
		Shelf shelf;
		Books book;
		Integer numb;
		String status;
		int idCategory;
		String name;
		Double fee;
		Integer duration;
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String title;
		List<Transaction> transactions;
		UserDao userDao = new UserDaoImpl();
		int pilih=0;

		while (pilih<13){
			System.out.println("1. Lihat Semua Buku");
			System.out.println("2. Tambah Buku");
			System.out.println("3. Lihat Semua Kategori Buku");
			System.out.println("4. Tambah Kategori Buku.");
			System.out.println("5. Ubah Kategori Buku");
			System.out.println("6. Pinjam Buku");
			System.out.println("7. Kembalikan Buku");
			System.out.println("8. Laporan Transaksi");
			System.out.println("9. Lihat Semua Buku Yang Sedang di Pinjam");
			System.out.println("10. Lihat Buku yang telat dikembalikan dan masih di pinjam serta total denda");
			System.out.println("11. Cari Buku berdasarkan judul buku");
			System.out.println("12.Cari Buku berdasarkan Kategori buku");
			System.out.println("13.Exit");

			System.out.println("Pilih:");
			pilih = Integer.parseInt(br.readLine());
			switch (pilih) {
				case 1:
					List<Books> books=booksDao.getAllBooks();
					numb=1;
					if (books.isEmpty()){
						System.out.println("Tidak ada list Buku");
					}else{
						for (Books book1: books) {
							ShowTable.printBook(book1,numb);
							numb++;
						}
					}
					break;
				case 2:
					if (user.getRole() == Role.Admin){
						System.out.print("Judul Buku : ");
						 title = br.readLine();
						System.out.print("Pengarang : ");
						String author = br.readLine();
						System.out.println("ID Rak (1-9) :");
						Integer idShelf = Integer.parseInt(br.readLine());
						System.out.println("ID Kategori : ");
						idCategory= Integer.parseInt(br.readLine());

						category = categoryDao.getCategoryById(idCategory);
						shelf = shelfDao.getShelfById(idShelf);
						book = new Books(title,author,true,shelf,category);

						status= booksDao.saveBook(book);
						if (status.equals("Success")){
							System.out.println("Berhasil menambahkan buku baru");
						}else {
							System.out.println("Gagal menambahkan buku");
						}
					}else{
						System.out.println("Fitur ini hanya untuk admin");
					}
					break;
				case 3:
					List<Category> categories = categoryDao.getAllCategories();
					numb=1;
					if (categories.isEmpty()){
						System.out.println("Tidak ada list kategori");

					}else{
						for (Category category1: categories) {
							ShowTable.printCategory(category1,numb);
							numb++;
						}
					}
					break;
				case 4:
					if (user.getRole()==Role.Admin){
						System.out.println("============Tambah Kategori========");
						System.out.print("Masukkan nama category : ");
						name = br.readLine();
						System.out.println("Masukkan biaya category : ");
						fee = Double.parseDouble(br.readLine());
						System.out.println("Masukkan durasi category : ");
						duration =  Integer.parseInt(br.readLine());
						//save category
						category = new Category(name, duration, fee);
						status = categoryDao.saveCategory(category);
						categories= categoryDao.getAllCategories();
						//create shelf
						String shelfCode;
						Integer length;
						if (categories.isEmpty()){
							length=1;
						}else {
							length=categories.size();
						}
						if (categories.size()<10){
							shelfCode= String.format("RK-0%d",length);
						}else{
							shelfCode= String.format("RK-%d",length);
						}
						shelf= new Shelf(shelfCode);
						shelfDao.saveShelf(shelf);

						if (status.equals("Success")){
							System.out.println("Berhasil menambahkan Kategori baru");
						}else {
							System.out.println("Gagal menambahkan Kategori");
						}
					}else{
						System.out.println("Fitur ini hanya untuk admin!");
					}
					break;
				case 5:
					if (user.getRole()==Role.Admin){
						System.out.println("============Ubah Kategori========");
						System.out.print("Masukkan Id Kategori: ");
						idCategory = Integer.parseInt(br.readLine());
						System.out.print("Masukkan nama category : ");
						name = br.readLine();
						System.out.print("Masukkan biaya category : ");
						fee = Double.parseDouble(br.readLine());
						System.out.print("Masukkan durasi category : ");
						duration =  Integer.parseInt(br.readLine());
						category = categoryDao.getCategoryById(idCategory);
						category.setName(name);
						category.setFeeBook(fee);
						category.setDuration(duration);

						status=categoryDao.updateCategory(category);
						if (status.equals("Success")){
							System.out.println("Berhasil mengubah Kategori");
						}else {
							System.out.println("Gagal mengubah Kategori");
						}
					}else {
						System.out.println("Fitur ini hanya untuk admin!");
					}
					break;
				case 6:
					if (user.getRole()==Role.Member){
						boolean isBorrowed=false;
						if (!(user.getTransaction()==null && user.getTransaction().isEmpty())){
							transactions = user.getTransaction();
							for (Transaction trx: transactions) {
								if (trx.getReturnDate()==null){
									isBorrowed=true;
								}else{
									isBorrowed=false;
								}
							}
						}else {
							isBorrowed=false;
						}

						if (isBorrowed==true){
							System.out.println("Kamu sedang memiminjam buku! kembalikan terlebih dahulu");
						}else {
							System.out.println("==============PEMINJAMAN BUKU=============");
							System.out.print("Masukan judul Buku yang ingin dipinjam : ");
							String judul = br.readLine();
							Books bookTransaction = booksDao.getBookSingleTitle(judul);

							if (bookTransaction != null) {
								System.out.println("==========Buku ditemukan=======");
								ShowTable.printBook(bookTransaction,1);
								if (bookTransaction.isAvailable()) {
									System.out.println("Masukkan tanggal pinjam (yyyy-MM-dd): ");
									String date = br.readLine();
									Date rentDate = null;
									try {
										Double payment = bookTransaction.getCategory().getFeeBook();
										rentDate = dateFormat.parse(date);
										Transaction trans = new Transaction(rentDate, payment, bookTransaction, user);
										status= transactionDao.saveTransaction(trans);
										bookTransaction.setAvailable(false);
										booksDao.updateBook(bookTransaction);

										if (status.equals("Success")){
											System.out.println("Berhasil meminjam");
											pilih=13;
										}else {
											System.out.println("Gagal meminjam");
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}
								} else {
									System.out.println("Maaf buku sedang dipinjam");
								}
							} else {
								System.out.println("Buku yang anda cari tidak ada");
							}
						}

					}else {
						System.out.println("Fitur ini hanya untuk member non admin");
					}
					break;
				case 7:
					if (user.getRole()==Role.Member){

						System.out.println("==============MENGEMBALIKAN BUKU=============");
						transactionDao.getAllTransaction();
						System.out.println("Masukkan ID transaksi : ");
						String idTrans = br.readLine();
						Transaction trans = transactionDao.getTransactionById(idTrans);
						if (trans.getReturnDate() == null) {
							System.out.println("Tanggal Kembali (yyyy-MM-dd): ");
							String date1 = br.readLine();
							Date returnDate;
							try {
								returnDate = dateFormat.parse(date1);
								trans.setReturnDate(returnDate);
								Long difdate = returnDate.getTime() - trans.getRentDate().getTime();
								Long difDay = difdate / (24 * 60 * 60 * 1000);
								Double penaltyfee;
								if (difDay > trans.getBookTransaction().getCategory().getDuration()) {
									int difference = (int) (difDay - trans.getBookTransaction().getCategory().getDuration());
									penaltyfee = difference * (trans.getPayment() * 0.05);
								} else {
									penaltyfee = 0.0;
								}
								Double totalBayar = penaltyfee + trans.getPayment();
								trans.setPenaltyfee(penaltyfee);
								trans.setTotalPayment(totalBayar);
								status=transactionDao.updateTransaction(trans);

								//ubah book jadi available
								book= booksDao.getBookById(trans.getBookTransaction().getId());
								book.setAvailable(true);
								booksDao.updateBook(book);

								if (status.equals("Success")){
									System.out.println("Transaksi pengembalian berhasil");
									pilih=13;
								}else {
									System.out.println("Transaksi gagal");
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}else {
						System.out.println("Fitur ini hanya untuk member non admin");
					}
					break;
				case 8:
					System.out.println("================LAPORAN TRANSAKSI===========");
					if (user.getRole()==Role.Admin){
						reportAdmin(br,transactionDao);
					}else{
						reportNonAdmin(br, transactionDao, user);
					}
					break;
				case 9:
					transactions = transactionDao.getAllTransaction();
					if (transactions.isEmpty()){
						System.out.println("Tidak ada buku yang dipinjam");
					}else {
						numb=1;
						for (Transaction trx: transactions) {
							if (user.getRole()==Role.Member){
								ShowTable.printBorrowedBookOrderByUser(trx,numb,user);
							}else {
								ShowTable.printBook(trx.getBookTransaction(),numb);
							}
							numb++;
						}
					}
					break;
				case 10:
					transactions = transactionDao.getAllTransaction();
					if (transactions.isEmpty()){
						System.out.println("Tidak ada transaksi buku");
					}else {
						numb=1;
						for (Transaction trx: transactions) {
							if (user.getRole()==Role.Member){
								ShowTable.printBookWithTrxOrderByUser(trx,numb,user);
							}else {
								ShowTable.printBookWithTrx(trx, numb);
							}
							numb++;
						}
					}
					break;
				case 11:
					System.out.print("Masukan judul Buku : ");
					title = br.readLine();
					book = booksDao.getBookSingleTitle(title);
					if (book!=null){
							ShowTable.printBook(book,1);
					}else {
						System.out.println("Buku tidak ditemukan!");
					}
					break;
				case 12:
					System.out.print("Masukan Kategori : ");
					name = br.readLine();
					category=categoryDao.getCategoryByName(name);
					if (category==null){
						System.out.println("Kategori tidak ditemukan!");
					}else{
						if (category.getBooks().isEmpty()){
							System.out.println("Tidak ada buku");
						}else{
							books = category.getBooks();
							numb=1;
							for (Books book1: books) {
								ShowTable.printBook(book1,numb);
								numb++;
							}
						}
					}
					break;
				case 13:
					System.out.println("Anda keluar dari program!");
					break;
				default:
					System.out.println("Menu yang anda pilih salah!");
					break;
			}
		}

    }

    public void reportAdmin(BufferedReader br, TransactionDao transactionDao) throws IOException {
		List<Transaction> transactionList;
		System.out.println("1. Lihat transaksi berdasarkan tanggal");
		System.out.println("2. Lihat transaksi berdasarkan bulan");
		System.out.println("3. Lihat total pajak");
		System.out.println("Masukkan angka pilihan: ");
		int choice = Integer.parseInt(br.readLine());
		switch (choice) {
			case 1:
				System.out.println("Masukkan tanggal transaksi (yyyy-MM-dd) [tanpa angka 0 di awal tanggal/bulan]:");
				String date = br.readLine();
				transactionList=transactionDao.getAllTransactionByDate(date);
				if (transactionList.isEmpty()){
					System.out.println("Tidak ada transaksi");
				}else {
					Integer number=1;
					for (Transaction trx: transactionList) {
						ShowTable.printTransaction(trx,number);
					}
				}
				break;
			case 2:
				System.out.println("Masukkan bulan transaksi peminjaman (MM) [tanpa angka 0 di awal]: ");
				Integer month = Integer.parseInt(br.readLine());
				System.out.println("Masukkan tahun (yyyy): ");
				Integer year = Integer.parseInt(br.readLine());
				transactionList=transactionDao.getAllTransactionByMonth(month, year);

				if (transactionList.isEmpty()){

					System.out.println("Tidak ada transaksi bulan ini");
				}else {
					Integer number=1;
					for (Transaction trx: transactionList) {
						ShowTable.printTransaction(trx,number);
					}
				}
				break;
			case 3:
				System.out.println("=========Lihat Total Pajak==========");
				System.out.println("Masukkan bulan (MM): ");
				Integer month2 = Integer.parseInt(br.readLine());
				System.out.println("Masukkan tahun (yyyy): ");
				Integer year2 = Integer.parseInt(br.readLine());
				List<Transaction> transactionsList = transactionDao.getAllTransactionByMonth(month2, year2);

				if (transactionsList.isEmpty()){
					System.out.println("Tidak ada transaksi bulan ini");
				}else{
					System.out.println(transactionsList+"trxx");
					Double total = 0.0;
					for (Transaction transactions : transactionsList) {
						total =total+ transactions.getPayment();
					}
					Double tax = total * 0.1;
					System.out.println("+-------------------------------------------+");
					System.out.println("|Total pajak bulan ini adalah : " + tax + "      |");
					System.out.println("+--------------------------------------------+");

				}

				break;
			default:
				System.out.println("menu yang anda pilih salah!");
				break;
		}
	}
	public void reportNonAdmin(BufferedReader br, TransactionDao transactionDao, User user) throws Exception {
		List<Transaction> transactionList;
		System.out.println("1. Lihat transaksi berdasarkan tanggal");
		System.out.println("2. Lihat transaksi berdasarkan bulan");
		System.out.println("Masukkan angka pilihan: ");
		int choice = Integer.parseInt(br.readLine());
		switch (choice) {
			case 1:
				System.out.println("Masukkan tanggal transaksi (yyyy-MM-dd) [tanpa angka 0 di awal tanggal/bulan]:");
				String date = br.readLine();
				transactionList=transactionDao.getAllTransactionByDate(date);
				if (transactionList.isEmpty()){
					System.out.println("Kamu tidak memiliki transaksi di tanggal tersebut.");
				}else{
					Integer number=1;
					for (Transaction trx: transactionList) {
						ShowTable.printTransactionOrderByUser(trx,number,user);
					}
				}
				break;
			case 2:
				System.out.println("Masukkan bulan transaksi peminjaman (MM) [tanpa angka 0 di awal]: ");
				Integer month = Integer.parseInt(br.readLine());
				System.out.println("Masukkan tahun (yyyy): ");
				Integer year = Integer.parseInt(br.readLine());
				transactionList=transactionDao.getAllTransactionByMonth(month,year);
				if (transactionList.isEmpty()){
					System.out.println("Kamu tidak memiliki transaksi di bulan tersebut.");
				}else{
					Integer number=1;
					for (Transaction trx: transactionList) {
						ShowTable.printTransactionOrderByUser(trx,number,user);
					}
				}
				break;
			default:
				System.out.println("menu yang anda pilih salah!");
				break;
		}
	}
}
