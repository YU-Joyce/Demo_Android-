package com.example.newone;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //khai báo các trường dữ liệu ở Lớp Contract qua và gọi thêm các hàm bổ sung
    EditText editHoTen, editSdt, editSearch;
    Button btnAdd, btnDelete;
    ListView lstContact;
    ArrayList<Contact> arrayList1;
    ArrayAdapter<Contact> adapter1;
    ArrayList<Contact> originalList;  // Danh sách gốc để không mất dữ liệu khi tìm kiếm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Cài đặt việc xử lý insets để ứng dụng hiển thị tốt hơn trên thiết bị có màn hình notch hoặc các thanh điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Sửa vấn đề với Insets
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return WindowInsetsCompat.CONSUMED;  // Trả về loại bỏ xử lý insets cũ
        });

        anhXa();  // Gán các thành phần giao diện

        arrayList1 = new ArrayList<>(); // Khởi tạo danh sách liên hệ hiện tại
        originalList = new ArrayList<>();  // Khởi tạo danh sách gốc để lưu trữ toàn bộ liên hệ

        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList1);
        lstContact.setAdapter(adapter1); // Kết nối adapter với ListView

        /*
        Xử lý sự kiện cho nút thêm và xóa liên hệ
         */
        btnAdd.setOnClickListener(v -> saveContact());
        btnDelete.setOnClickListener(v -> deleteContact());


        /*
        Xử lý sự kiện cho thanh tìm kiếm
         */
        editSearch.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP) {  // Kiểm tra khi người dùng thả phím
                String query = editSearch.getText().toString(); // Lấy chuỗi tìm kiếm từ trường nhập
                filterContacts(query); // Gọi hàm lọc danh sách liên hệ dựa trên chuỗi tìm kiếm
            }
            return false;
        });
    }


    //Gán lại các giá trị ở dòng 20 -> 22
    public void anhXa() {
        editHoTen = findViewById(R.id.editHoten);
        editSdt = findViewById(R.id.editSdt);
        editSearch = findViewById(R.id.editSearch);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        lstContact = findViewById(R.id.lstDanhBa);
    }

    public void saveContact() {
        String ht = editHoTen.getText().toString().trim();
        String sdt = editSdt.getText().toString().trim();

        // Kiểm tra nếu người dùng để trống họ tên hoặc số điện thoại
        if (ht.isEmpty() || sdt.isEmpty()) {
            Toast.makeText(this, "Họ tên và số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact ct = new Contact(ht, sdt); // Tạo đối tượng Contact mới

        // Kiểm tra nếu liên hệ này chưa tồn tại trong danh sách
        if (!arrayList1.contains(ct)) {
            arrayList1.add(ct);
            originalList.add(ct);  // Cập nhật danh sách gốc
            adapter1.notifyDataSetChanged();
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Trùng thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteContact() {
        String ht = editHoTen.getText().toString().trim();  // Lấy họ tên từ trường nhập liệu
        Contact toDelete = null;
        // Duyệt qua danh sách liên hệ để tìm liên hệ cần xóa
        for (Contact contact : arrayList1) {
            if (contact.getHoTen().equals(ht)) {
                toDelete = contact;
                break;
            }
        }

        // Nếu tìm thấy liên hệ cần xóa
        if (toDelete != null) {
            arrayList1.remove(toDelete); // Xóa khỏi danh sách hiện tại
            originalList.remove(toDelete);  // Xóa khỏi danh sách gốc
            adapter1.notifyDataSetChanged();  // Cập nhật giao diện ListView
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Không tìm thấy", Toast.LENGTH_SHORT).show();
        }
    }

    // lọc danh sách liên hệ dựa trên chuỗi tìm kiếm
    public void filterContacts(String query) {
        ArrayList<Contact> filteredList = new ArrayList<>();

        for (Contact contact : originalList) {  // Lọc từ danh sách gốc để không mất dữ liệu ban đầu
            // Kiểm tra nếu query có trong tên hoặc bắt đầu bằng số điện thoại
            if (contact.getHoTen().toLowerCase().contains(query.toLowerCase()) ||
                    contact.getSoDienThoai().startsWith(query)) {  // Thay đổi tại đây
                filteredList.add(contact);
            }
        }

        // Cập nhật danh sách hiển thị với kết quả tìm kiếm
        adapter1.clear();
        adapter1.addAll(filteredList);
        adapter1.notifyDataSetChanged();
    }
}
