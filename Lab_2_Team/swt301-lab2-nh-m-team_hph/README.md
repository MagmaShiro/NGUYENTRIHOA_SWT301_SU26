[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/NU530x9m)
# [Mã lớp]\_Nhóm[XX]\_[Tên dự án]

> **Môn:** SWT301 — Software Testing | **GVHD:** [Tên giảng viên]

<!-- Sau khi nhóm được tạo repo, thay <org> và <repo> bằng đường dẫn thực -->
![CI Status](https://github.com/<org>/<repo>/actions/workflows/ci.yml/badge.svg)

---

## 👥 Thành viên nhóm

| MSSV | Họ tên | GitHub | Vai trò |
|------|--------|--------|---------|
| DE190001 | NguyễnTri Hòa | @MagmaShiro | Leader |
| DE201054 | Nguyễn Tấn Phú   | @nguyentanphu6e | Dev     |
| DE200002 | Nguyễn Đức Hải      | @0x23null | Dev     |


---

## 📋 Mô tả dự án

<!-- Mô tả ngắn gọn (2–4 câu):
     - Hệ thống/tính năng nhóm xây dựng là gì?
     - Công nghệ sử dụng (Java 21 LTS, Maven, JUnit 5, ...)
-->

---

## 📁 Cấu trúc thư mục

```
project-root/
├── src/                          ← Source code Java (nhóm viết)
├── tests/                        ← Unit tests JUnit 5 (nhóm viết)
│
├── README.md                     ← File này — điền thông tin nhóm
│
├── SV_guide.md                   ← 📖 Hướng dẫn làm việc nhóm (ĐỌC TRƯỚC)
├── pom.xml                       ← Maven: JUnit 5 + JaCoCo + Checkstyle
├── checkstyle.xml                ← Quy tắc coding style

```

---

## ⚡ Bắt đầu nhanh

> **Đọc [`SV_guide.md`](SV_guide.md) trước** — có hướng dẫn từng bước và code mẫu AccountService.

### Yêu cầu môi trường

| Công cụ | Phiên bản | Tải về |
|---------|-----------|--------|
| Java JDK | 21 (LTS) | https://adoptium.net |
| Maven | 3.8+ | https://maven.apache.org |
| IDE | IntelliJ IDEA | https://jetbrains.com/idea |
| Git | 2.x+ | https://git-scm.com |

### Mở project lần đầu

```bash
# 1. Clone repo về máy
git clone <link-repo-github-classroom-của-nhóm>
cd <tên-repo>

# 2. Mở IntelliJ: File → Open → chọn thư mục → Open as Project
#    IntelliJ tự nhận pom.xml và tải dependencies

# 3. Kiểm tra project chạy được
mvn compile
```

### Lệnh Maven thường dùng

```bash
mvn compile              # Biên dịch source code
mvn test                 # Chạy tất cả unit tests
mvn test jacoco:report   # Test + tạo báo cáo coverage HTML
mvn checkstyle:check     # Kiểm tra coding style
mvn verify               # Tất cả trong một lệnh
```

Xem coverage: mở `target/site/jacoco/index.html` sau khi chạy `mvn test jacoco:report`.

---

---

## 🌿 Quy tắc làm việc nhóm

### Branch & Pull Request
```
❌ KHÔNG push thẳng vào main
✅ Tạo branch → code → commit → push → tạo PR → 1 người review → merge
```

| Loại công việc | Tên branch |
|---------------|-----------|
| Tính năng mới | `feature/tên-tính-năng` |
| Sửa lỗi | `fix/mô-tả-lỗi` |
| Viết test | `test/tên-class` |
| Tài liệu | `docs/tên-file` |

### Commit message
Dùng format: `type: mô tả ngắn gọn bằng tiếng Anh hoặc Việt`

```
feat: add BankAccount with deposit/withdraw
test: add unit tests for AccountService transfer
fix: handle null input in withdraw()
docs: update AI_AUDIT_LOG with ChatGPT session
refactor: extract validateAmount() from placeOrder()
chore: update pom.xml groupId to se1801.nhom05
```
