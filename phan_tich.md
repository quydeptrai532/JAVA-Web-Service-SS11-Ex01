Phần 1 - Phân tích logic
Mã nguồn hiện tại đang gặp 2 lỗi logic nghiêm trọng dẫn đến việc tính sai phí:

Lỗi tính phí cân nặng (Sai hàm làm tròn):

Nguyên nhân: Mã nguồn đang sử dụng Math.floor(weightKg - 1). Hàm Math.floor() sẽ làm tròn xuống.

Ví dụ: Nếu gói hàng nặng 1.5kg, Math.floor(1.5 - 1) sẽ bằng 0. Khách hàng chỉ bị thu 50.000 VND (bằng với mức 1kg) thay vì phải trả thêm 10.000 VND cho phần lẻ theo quy tắc "Mỗi kg tiếp theo (hoặc phân số của kg)".

Cách sửa: Cần sử dụng Math.ceil() để làm tròn lên phần cân nặng dư.

Lỗi tính phí khoảng cách (Hiệu ứng vách đá - Cliff Effect):

Nguyên nhân: Cách code hiện tại nhân tổng khoảng cách với đơn giá của từng mốc (distanceKm * 5000 hoặc * 4000), điều này tạo ra một lỗ hổng logic về giá ở các điểm giao cắt.

Ví dụ: * Khoảng cách 49km: Rơi vào nhánh < 50, phí là 49 * 5000 = 245.000 VND.

Khoảng cách 50km: Rơi vào nhánh >= 50, phí là 50 * 4000 = 200.000 VND.

=> Giao hàng 49km lại đắt hơn 50km! Khách hàng phàn nàn là hoàn toàn chính xác.

Cách sửa: Phí vận chuyển cần được tính theo dạng lũy tiến (tiered) giống như tính tiền điện. 10km đầu miễn phí; 40km tiếp theo (từ 10 đến 50) tính giá 5.000đ; phần còn lại từ 50km trở lên tính giá 4.000đ.