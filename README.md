<div align="center">

# 🛒 E-Shop (Advanced Programming)
**Tutorial & Exercise Reflections**

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)

Created by **Ahmad Nizar Sauki** | **2306152046**
*Fakultas Ilmu Komputer, Universitas Indonesia*

---
</div>

## 📚 Table of Contents
* **[Module 1: Coding Standards](#module-1-coding-standards)**
  * [Reflection 1: Clean Code & Secure Coding](#reflection-1-clean-code--secure-coding)
  * [Reflection 2: Unit Testing & Functional Test Clean Code](#reflection-2-unit-testing--functional-test-clean-code)
* **[Module 2: CI/CD & DevOps](#module-2-cicd--devops)**
  * [Reflection: CI/CD Implementation & Code Quality](#reflection-cicd-implementation--code-quality)
* **[Module 3: Maintainability & OO Principles](#module-3-maintainability--oo-principles)**
  * [Reflection: SOLID Principles](#reflection-solid-principles)
    * [1. Single Responsibility Principle (SRP)](#1-single-responsibility-principle-srp)
    * [2. Open-Closed Principle (OCP)](#2-open-closed-principle-ocp)
    * [3. Liskov Substitution Principle (LSP)](#3-liskov-substitution-principle-lsp)
    * [4. Interface Segregation Principle (ISP)](#4-interface-segregation-principle-isp)
    * [5. Dependency Inversion Principle (DIP)](#5-dependency-inversion-principle-dip)

---

## Module 1: Coding Standards

### Reflection 1: Clean Code & Secure Coding

#### 1. Clean Code Principles
Dalam pengerjaan tugas ini, saya telah menerapkan beberapa prinsip *Clean Code* untuk menjaga kualitas dan keterbacaan kode:

* **Meaningful Names (Penamaan yang Jelas):**
  Saya menghindari penggunaan variabel satu huruf. Semua variabel diberi nama yang deskriptif sesuai tujuannya.
  * **Repository vs Service Naming:** Saya menerapkan strategi penamaan yang berbeda sesuai konteks. Pada *Repository*, method diberi nama sederhana (seperti `create`, `delete`, `edit`) karena pemanggilannya sudah cukup jelas (misal: `productRepository.delete(productId)`). Namun, pada *Service*, saya menggunakan nama yang lebih spesifik (seperti `deleteProductById`). Hal ini dilakukan untuk menghindari ambiguitas di masa depan jika *ProductService* berkembang menjadi lebih kompleks, sehingga pemanggilan `service.deleteProductById(...)` lebih eksplisit maknanya dibandingkan hanya `service.delete(...)`.

* **Single Responsibility Principle:**
  Saya telah memisahkan tanggung jawab kode ke dalam package yang sesuai: `Controller` (mengatur request), `Service` (logika bisnis), dan `Repository` (akses data). Setiap class hanya memiliki satu alasan untuk berubah.

* **Functions:**
  Method yang saya buat dijaga agar tetap pendek dan hanya melakukan satu hal (*do one thing*). Contohnya method `delete` hanya fokus menghapus data tanpa melakukan validasi input yang berlebihan (validasi dilakukan di layer lain atau method terpisah).

#### 2. Secure Coding Practices
Saya menerapkan praktik keamanan dasar pada manajemen ID produk:

* **UUID vs Sequential Integer:** Saya menggunakan `UUID` (Universally Unique Identifier) yang digenerate secara random dan dikonversi menjadi String, alih-alih menggunakan integer berurut. Hal ini dilakukan untuk mencegah *Insecure Direct Object References (IDOR)* atau penebakan ID produk (enumeration attack).
* **Output Encoding/Limitation:** Pada halaman list produk, saya hanya menampilkan 8 karakter pertama dari UUID untuk alasan kerapian (*readability*) dan meminimalisir eksposur data ID mentah secara penuh ke pengguna, meskipun ID penuh tetap digunakan di URL untuk keperluan editing/deleting.

#### 3. Version Control & Workflow Management
Saya menyadari pentingnya *version control* yang rapi dalam pengembangan fitur:

* **Feature Branching:** Saya membuat branch terpisah untuk setiap fitur (`list-product`, `edit-product`, `delete-product`) dan baru melakukan *merge* ke `main` setelah fitur tersebut selesai dan stabil.
* **Atomic Commits:** Saya melakukan commit secara parsial dan berkala (misalnya, setelah selesai mengupdate Controller, saya langsung commit sebelum lanjut ke file lain). Hal ini membuat *git history* menjadi rapi dan memudahkan pelacakan perubahan jika terjadi *error*.

#### 4. Evaluasi & Perbaikan (Self-Reflection)
* **Kompleksitas Fitur:** Saya menyadari bahwa implementasi fitur *Delete* ternyata lebih sederhana dibandingkan fitur *Edit*. Fitur *Edit* memerlukan logika tambahan untuk mencari ID produk terlebih dahulu (*retrieval*) dan melakukan *mapping* data baru ke data lama, sedangkan *Delete* hanya memerlukan ID untuk menghapus data dari list.
* **Naming Clarity:** Selama proses *debugging*, saya tidak menemukan penamaan variabel yang membingungkan, yang menandakan bahwa prinsip *Meaningful Names* sudah cukup membantu saya dalam memelihara kode ini.

---

### Reflection 2: Unit Testing & Functional Test Clean Code

#### 1. Unit Testing & Code Coverage
Setelah menulis unit test untuk fitur Edit dan Delete, saya merasa lebih percaya diri dalam memastikan keandalan kode saya. Namun, saya juga mempelajari bahwa **100% Code Coverage tidak menjamin kode bebas dari bug atau error**.

Code coverage hanya mengukur persentase baris kode yang dieksekusi selama pengujian, tetapi tidak memverifikasi kebenaran logika di dalamnya.
> **Contoh:** Pada fitur Edit, misalnya saya menulis kode untuk memperbarui data produk:
> `this.quantity = newProduct.getQuantity();`
> tetapi saya **lupa** menulis baris untuk memperbarui nama produk (`this.name = ...`).
>
> Jika unit test saya hanya mengecek apakah kuantitas berubah (tanpa mengecek apakah nama juga berubah), maka test akan *Passed* dan coverage code tersebut **100%** (karena baris update quantity dieksekusi). Padahal, secara logika bisnis, kode tersebut salah karena gagal memperbarui nama produk. Ini menunjukkan bahwa kualitas *assertion* dalam test jauh lebih penting daripada sekadar angka coverage.

#### 2. Clean Code in Functional Tests
Terkait tantangan pembuatan functional test baru untuk memverifikasi jumlah item dalam daftar produk, saya menganalisis kode tersebut sebagai berikut:

* **Code Cleanliness & Quality Issue:**
  Saya berpendapat bahwa membuat functional test suite baru dengan menyalin (*copy-paste*) prosedur setup dan variabel instance dari `CreateProductFunctionalTest.java` **akan menurunkan kualitas kode (reduce code quality)**. Hal ini menyebabkan kode menjadi tidak bersih (*unclean*) karena melanggar prinsip **DRY (Don't Repeat Yourself)**.

* **Potential Issues & Reasons:**
  Masalah utama yang timbul adalah **Code Duplication** (Duplikasi Kode).
  Jika konfigurasi setup ditulis berulang-ulang di setiap class test, maka kode akan menjadi sulit dirawat (*hard to maintain*). Contohnya, jika di masa depan kita perlu mengubah konfigurasi port atau `baseUrl`, kita harus melakukan perubahan manual di semua file test satu per satu. Ini rentan terhadap *human error* dan inkonsistensi.

* **Improvement Suggestion:**
  Untuk memperbaiki masalah tersebut dan membuat kode lebih bersih, saya menyarankan penggunaan teknik **Inheritance** (Pewarisan) dengan langkah-langkah berikut:
  1. Membuat kelas induk bernama `BaseFunctionalTest` yang berisi semua konfigurasi umum (`baseUrl`, port, driver setup, dan `@BeforeEach`).
  2. Mengubah `CreateProductFunctionalTest` dan class test baru lainnya agar melakukan **extends** terhadap `BaseFunctionalTest`.

  Dengan cara ini, duplikasi kode dapat dihilangkan sepenuhnya, dan setup pengujian terpusat di satu tempat.

---

## Module 2: CI/CD & DevOps

### Reflection: CI/CD Implementation & Code Quality

#### 1. Code Quality Issues & Fixing Strategy
Selama mengerjakan *exercise* ini, saya menemukan dan memperbaiki cukup banyak masalah kualitas kode yang dideteksi oleh *tools* analisis statis (*linter*) yang terintegrasi di *pipeline* CI/CD. Beberapa *issue* utama yang saya perbaiki antara lain:

* **Unused Code & Redundant Modifiers:** Saya menemukan banyak potongan kode yang tidak lagi terpakai atau berlebihan, seperti *unused imports*, *empty setUp method* yang tidak terpakai di `ProductRepositoryTest`, serta modifier `public` yang redundant (mengingat JUnit 5 tidak mewajibkan *public modifier* untuk kelas atau method test). Selain itu, terdapat deklarasi `throws java.lang.Exception` pada method yang sebenarnya tidak melempar *exception*.
  * **Strategi:** Saya menghapus seluruh kode mati dan modifier berlebih tersebut secara manual agar *codebase* menjadi lebih ringkas, bersih, dan mematuhi konvensi Java modern.
* **Dependency Injection Practice:** Terdapat *code smell* terkait penggunaan *field injection* (biasanya menggunakan anotasi `@Autowired` langsung pada *field*).
  * **Strategi:** Saya melakukan *refactor* dengan menggantinya menjadi *constructor injection*. Pendekatan ini lebih disarankan karena membuat *dependencies* bersifat *immutable* (bisa dijadikan `final`) dan jauh lebih mudah untuk di-*mock* saat melakukan *unit testing*.
* **Testing Code Quality:** Beberapa *test suite* memiliki kelemahan fungsionalitas dan desain, seperti ketiadaan *assertions* (*missing assertions*), *base test class* yang tidak dilabeli sebagai abstrak, dan method `contextLoads` yang kosong tanpa penjelasan.
  * **Strategi:** Saya memperkuat *test* dengan menambahkan *assertion* yang tepat untuk memastikan fungsionalitas benar-benar teruji. Saya juga mengubah kelas dasar pengujian menjadi `abstract` agar tidak bisa diinisiasi secara langsung, serta menambahkan komentar penjelas (*explanatory comment*) pada method `contextLoads` yang sengaja dibiarkan kosong agar linter memahaminya sebagai *behavior* yang disengaja.
* **Security & Configuration Maintainability:** Terdapat peringatan terkait keamanan pada GitHub Actions dan struktur konfigurasi (*build file*).
  * **Strategi:** Saya menerapkan prinsip *least privilege* dengan membatasi *permissions* GitHub Actions (pada alur kerja Scorecard) menjadi hanya `contents:read`. Selain itu, saya juga merapikan dependensi dengan mengelompokkannya berdasarkan tipe konfigurasi agar lebih mudah di-*maintain* (*better maintainability*).

#### 2. CI/CD Implementation Evaluation
Berdasarkan pengerjaan *tutorial* dan *exercise* ini, saya yakin bahwa implementasi saat ini sudah sepenuhnya memenuhi definisi *Continuous Integration* (CI) dan *Continuous Deployment* (CD).

Dari sisi **Continuous Integration**, setiap kali ada proses *push* atau *Pull Request* yang mengarah ke *branch* utama di GitHub, GitHub Actions akan secara otomatis menjalankan proses *build*, mengeksekusi seluruh *test suite* (unit & functional), dan menganalisis kualitas kode. Hal ini memastikan bahwa kode baru terintegrasi dengan mulus tanpa merusak fungsionalitas yang sudah ada.

Dari sisi **Continuous Deployment**, *pipeline* telah terhubung langsung dengan *Platform as a Service* (Koyeb) berbasis Docker yang merespons perubahan secara *real-time*. Begitu seluruh proses CI dinyatakan lulus (*passed*), sistem akan otomatis menarik (*pull*) versi rilis terbaru dan melakukan *deployment* ke *server public* tanpa memerlukan perintah manual atau intervensi langsung dari *developer*.

## Module 3: Maintainability & OO Principles

### Reflection: SOLID Principles

#### 1. Single Responsibility Principle (SRP)
saya telah mengimplementasikan SRP setelah memodifikasi kode awal.
SRP menyatakan bahwa sebuah *class* harus memiliki satu dan hanya satu alasan untuk berubah, yang berarti *class* tersebut hanya boleh mengenkapsulasi satu aspek fungsionalitas atau satu tanggung jawab saja.
kode saya sebelumnya belum mematuhi SRP karena class `CarController` ditulis dan digabungkan di dalam file `ProductController.java`. Hal ini membuat file tersebut memikul dua tanggung jawab sekaligus: mengelola *HTTP request* untuk entitas `Product` dan juga entitas `Car`.

Untuk menerapkan SRP, **saya telah merubah kode saya** dengan memisahkan `CarController` ke dalam filenya sendiri (`CarController.java`).
* Sekarang, `ProductController.java` murni hanya menangani fungsionalitas dan alur *routing* untuk produk.
* `CarController.java` berdiri sendiri dan secara eksklusif hanya fokus menangani fungsionalitas mobil.

Dengan pemisahan ini, masing-masing *controller* kini hanya memiliki satu alasan untuk berubah.

#### 2. Open-Closed Principle (OCP)

saya telah memodifikasi kode agar mematuhi OCP, khususnya pada package repository.

OCP menyatakan bahwa entitas *software* harus terbuka untuk perluasan (*open for extension*) tetapi tertutup untuk modifikasi (*closed for modification*).

Sebelumnya, method `update` pada `CarRepository` saya melanggar OCP karena melakukan *update* atribut secara manual satu per satu (`car.setCarName(...)`, `car.setCarColor(...)`, dst.). Jika ada penambahan atribut baru pada model `Car` (misalnya `price`), saya harus memodifikasi *source code* `CarRepository`.

Saya telah memodifikasi method tersebut agar langsung mengganti objek `Car` lama dengan objek `updatedCar` di dalam *list* (`carData.set(i, updatedCar)`). Dengan ini, jika entitas `Car` diperluas dengan atribut baru, kode di `CarRepository` tidak perlu dimodifikasi sama sekali.

#### 3. Liskov Substitution Principle (LSP)

saya telah mengimplementasikan LSP setelah memodifikasi kode awal.

LSP sendiri menyatakan bahwa objek dari sebuah *superclass* harus dapat digantikan oleh objek dari *subclass*-nya tanpa memengaruhi kebenaran (*correctness*) program. Subclass harus bisa menggantikan kelas dasarnya tanpa mengubah properti program yang diinginkan, seperti konsistensi functional.

Sebelumnya, kode saya belum mematuhi LSP karena `CarController` melakukan *extends* terhadap `ProductController`. Ini adalah pendekatan yang keliru karena `CarController` bukanlah substitusi yang valid untuk `ProductController`. Jika kita menggantikan `ProductController` dengan `CarController`, maka method yang diwariskan (seperti `productListPage`) akan terpengaruh oleh `@RequestMapping("/car")` milik `CarController`. Akibatnya, *endpoint* untuk produk akan berubah menjadi `/car/list` yang jelas merusak konsistensi dan logika *routing*.

Untuk menyesuaikan kode dengan LSP, **saya memodifikasi kode dengan menghapus `extends ProductController` pada `CarController`**. Saya juga menghapus pemanggilan `super(service)` di *constructor*. Sekarang, `CarController` berdiri sendiri secara independen tanpa mewarisi *behavior* yang tidak relevan, sehingga kebenaran dan konsistensi program tetap terjaga.

#### 4. Interface Segregation Principle (ISP)

struktur kode sudah menerapkan ISP sejak awal dan tidak memerlukan modifikasi lebih lanjut.

ISP menyatakan bahwa klien tidak boleh dipaksa untuk bergantung pada *interface* yang tidak mereka gunakan. Oleh karena itu, *interface* yang besar harus dipecah menjadi lebih kecil dan spesifik agar klien hanya perlu mengetahui metode yang benar-benar relevan bagi mereka.

Pada proyek ini, kode dari tutorial sudah mengimplementasikan ISP dengan memisahkan *interface* untuk layanan product dan car, yaitu `ProductService` dan `CarService`. Daripada menggabungkan keduanya ke dalam satu *interface* besar (misalnya `EshopService`) yang akan memaksa kelas implementasi dan *controller* untuk mengetahui metode yang tidak relevan, lebih baik memang membaginya sesuai domain masing-masing. `CarController` hanya bergantung pada metode-metode yang spesifik untuk entitas mobil.

#### 5. Dependency Inversion Principle (DIP)

saya telah mengimplementasikan DIP.

Prinsip DIP menyatakan bahwa modul tingkat tinggi (*high-level modules*) tidak boleh bergantung pada modul tingkat rendah (*low-level modules*). Keduanya harus bergantung pada abstraksi (*abstractions*). Selain itu, abstraksi tidak boleh bergantung pada detail, melainkan detail yang harus bergantung pada abstraksi.

Sebelumnya, kode saya melanggar prinsip ini karena `CarController` (modul tingkat tinggi) bergantung langsung pada kelas implementasi konkret `CarServiceImpl` (modul tingkat rendah) melalui deklarasi variabel:
`@Autowired private CarServiceImpl carservice;`

Untuk menerapkan DIP, **saya memodifikasi kode dengan mengubah tipe dependensinya menjadi *interface***.
Sekarang kodenya menjadi:
`@Autowired private CarService carservice;`

Dengan ini, `CarController` hanya bergantung pada kontrak abstraksi dari `CarService`, bukan pada detail implementasinya. Hal ini membuat *controller* menjadi terlepas (*decoupled*) dari logika spesifik, sehingga jika di masa depan ada implementasi service baru, *controller* tidak perlu diubah.

---

### Advantages of Applying SOLID Principles
Menerapkan prinsip S.O.L.I.D memberikan kenyamanan dan keuntungan krusial pada project, terutama dalam hal pemeliharaan (*maintainability*), skalabilitas, dan kemudahan pengujian (*testability*).

* **Pemeliharaan Kode yang Terisolasi (Maintainability):** Dengan **SRP**, setiap modul memiliki satu fokus tanggung jawab. Sebagai contoh, dengan memisahkan `CarController` dan `ProductController`, jika ada *bug* atau penambahan fitur khusus untuk entitas car, saya hanya perlu meng-update `CarController.java`. Saya tidak perlu takut perubahan tersebut akan merusak sistem *routing* untuk product.
* **Fleksibilitas Menghadapi Perubahan Kebutuhan (Flexibility):** Dengan menerapkan **OCP** pada `CarRepository` (mengganti objek secara langsung di dalam list, bukan mengupdate atribut satu per satu), proyek menjadi sangat dinamis. Jika di masa depan model `Car` bertambah panjang dengan atribut baru (seperti harga, jenis mesin, atau plat nomor), saya tidak perlu membuka dan mengubah logika *setter* di *repository* lagi. Bahkan ini sesuai dengan prinsip DRY(Don't Repeat Yourself).
* **Pengujian yang Jauh Lebih Mudah (Testability):** Penerapan **DIP** memaksa modul tingkat tinggi (*Controller*) bergantung pada *Interface* (*Service*). Contoh nyatanya, saat melakukan *Unit Testing* pada `CarController`, saya bisa dengan mudah menyuntikkan (*inject*) *mock* object dari `CarService` tanpa perlu memusingkan kompleksitas implementasi asli dari `CarServiceImpl` ataupun konfigurasi eksternal lainnya.

---

### Disadvantages of Not Applying SOLID Principles
Mengabaikan prinsip S.O.L.I.D akan menghasilkan utang teknis(*technical debt*) yang membuat *codebase* menjadi kaku(*rigid*), rapuh(*fragile*), dan mempersulit kolaborasi *developer*.

* **Kode Rentan Bug Akibat Efek Samping (Fragile):** Ini terjadi sebelum saya menerapkan **LSP**. Saat `CarController` secara paksa mewarisi `ProductController`, method turunan seperti `productListPage` secara otomatis mendapatkan *base path* dari class anak (`/car`). Akibatnya, *endpoint* untuk menampilkan produk malah tertimpa, menyebabkan perilaku sistem (URL *routing*) menjadi tidak konsisten.
* **Ketergantungan Kuat yang Menyulitkan Perubahan (High Coupling):** Sebelum menerapkan **DIP**, `CarController` bergantung langsung pada `CarServiceImpl`. Jika suatu saat implementasi bisnis berubah drastis (misalnya peralihan dari arsitektur *monolith* menjadi pemanggilan API *microservice* eksternal), saya tidak hanya mengubah *Service*, tetapi terpaksa harus memodifikasi kode *Controller* juga. Perubahan sekecil apa pun akan memberikan efek ke lapisan-lapisan lainnya.
* **Terjadinya Kode yang membengkak(Spaghetti Code):** Tanpa mematuhi **SRP**, file seperti `ProductController.java` akan menjadi "tong sampah" untuk menampung seluruh *request handling* di aplikasi. Jika proyek ini terus diperbesar menjadi *e-commerce* utuh (dengan entitas *User*, *Cart*, dll.), file tersebut akan memiliki ribuan baris kode yang sangat sulit dinavigasi dan memperbesar kemungkinan terjadinya *merge conflict* ketika dikerjakan secara berkelompok.