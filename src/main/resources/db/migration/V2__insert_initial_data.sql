-- ============================================================
-- FILE: V2__insert_initial_data.sql
-- NỘI DUNG: Khởi tạo dữ liệu thực tế cho hệ thống Pet Shop
-- ============================================================

-- =========================
-- 1. USERS (Thêm 2 User khách hàng, ID sẽ là 3 và 4)
-- =========================
INSERT INTO users (full_name, email, password, phone, avata, status, role_id)
VALUES 
    ('Nguyen Van An', 'nguyenvanan@gmail.com', '$2a$10$XH8dd1VUtj/Dxbc4cklLhuHANErpXed9aDpmWf2blAOu1RpO4BXF.', '0988123456', 'https://example.com/avatars/user1.jpg', 'ACTIVE', 2),
    ('Tran Thi Bich', 'tranthibich@gmail.com', '$2a$10$Jg4y7uOJ.8CjvN8bQqGpCOcFKK5VoS2Le2pb3tB0Y6XIHN7SdXSCO', '0977654321', 'https://example.com/avatars/user2.jpg', 'ACTIVE', 2);

-- =========================
-- 2. ADDRESSES (Địa chỉ giao hàng cho khách)
-- =========================
INSERT INTO addresses (user_id, receiver_name, phone, province, district, ward, street, is_default)
VALUES 
    (3, 'Nguyen Van An', '0988123456', 'Thành phố Hà Nội', 'Quận Cầu Giấy', 'Phường Dịch Vọng', 'Số 12 Ngõ 68 Cầu Giấy', TRUE),
    (3, 'Nguyen Van An (Cơ quan)', '0988123456', 'Thành phố Hà Nội', 'Quận Nam Từ Liêm', 'Phường Mỹ Đình 1', 'Tòa nhà Song Da, Đường Phạm Hùng', FALSE),
    (4, 'Tran Thi Bich', '0977654321', 'Thành phố Hồ Chí Minh', 'Quận 1', 'Phường Bến Nghé', '123 Đường Nguyễn Huệ', TRUE),
    (4, 'Tran Thi Bich (Nhà bố mẹ)', '0912345678', 'Thành phố Hồ Chí Minh', 'Quận Bình Thạnh', 'Phường 25', '45/12 Đường D2', FALSE),
    (1, 'System Admin Center', '0901111111', 'Thành phố Hà Nội', 'Quận Đống Đa', 'Phường Láng Hạ', '88 Đường Láng', TRUE);

-- =========================
-- 3. CATEGORIES (Danh mục sản phẩm thú cưng)
-- =========================
INSERT INTO categories (name, description, image, status)
VALUES 
    ('Máy Cho Ăn Tự Động', 'Các thiết bị hẹn giờ và điều khiển qua app để cho chó mèo ăn', 'https://example.com/categories/feeder.jpg', 'ACTIVE'),
    ('Máy Sấy & Chăm Sóc Lông', 'Máy sấy lông chuyên dụng, lồng sấy thông minh cho thú cưng', 'https://example.com/categories/dryer.jpg', 'ACTIVE'),
    ('Thức Ăn & Hạt Dinh Dưỡng', 'Thức ăn hạt, pate cao cấp cho chó và mèo', 'https://example.com/categories/food.jpg', 'ACTIVE'),
    ('Nhà Vệ Sinh & Cát Mèo', 'Khay vệ sinh, máy dọn phân tự động và cát vệ sinh', 'https://example.com/categories/litter.jpg', 'ACTIVE'),
    ('Đồ Chơi & Phụ Kiện', 'Cây cào móng, vòng cổ, dây dắt và đồ chơi thông minh', 'https://example.com/categories/toys.jpg', 'ACTIVE');

-- =========================
-- 4. PRODUCTS (Sản phẩm thực tế)
-- =========================
INSERT INTO products (category_id, name, description, price, stock, brand, weight, status)
VALUES 
    (1, 'Máy Cho Ăn Tự Động PETKIT Fresh Element Solo', 'Dung tích 2.8L, kết nối Wifi điều khiển qua smartphone, chống kẹt hạt thông minh', 1450000.00, 25, 'PETKIT', 2.50, 'ACTIVE'),
    (2, 'Lồng Sấy Khô Thú Cưng Homerunpet Drybo Plus', 'Công nghệ sấy xoay chiều 360 độ, kiểm soát nhiệt độ an toàn, độ ồn thấp dưới 40dB', 6890000.00, 10, 'Homerunpet', 8.80, 'ACTIVE'),
    (3, 'Thức Ăn Hạt Cho Mèo Trưởng Thành Royal Canin Fit32', 'Cung cấp dinh dưỡng cân bằng, hỗ trợ búi lông và duy trì cân nặng lý tưởng (Túi 2kg)', 380000.00, 100, 'Royal Canin', 2.00, 'ACTIVE'),
    (4, 'Máy Dọn Vệ Sinh Cho Mèo Automatic CATLINK BayMax', 'Tự động sàng lọc cát sau khi mèo đi vệ sinh, gom mùi hiệu quả, theo dõi sức khỏe qua App', 4200000.00, 15, 'CATLINK', 11.00, 'ACTIVE'),
    (5, 'Cây Cào Móng Cho Mèo Kết Hợp Ôm Lượn Sóng Puncat', 'Chất liệu giấy carton gợn sóng cao cấp, giúp mèo giải tỏa căng thẳng và bảo vệ sofa', 195000.00, 50, 'Puncat', 1.20, 'ACTIVE');

-- =========================
-- 5. PRODUCT IMAGES (Hình ảnh sản phẩm)
-- =========================
INSERT INTO product_images (product_id, image_url, is_thumbnail)
VALUES 
    (1, 'https://example.com/products/petkit-feeder-main.jpg', TRUE),
    (1, 'https://example.com/products/petkit-feeder-detail.jpg', FALSE),
    (2, 'https://example.com/products/drybo-plus-main.jpg', TRUE),
    (3, 'https://example.com/products/royal-canin-fit32.jpg', TRUE),
    (4, 'https://example.com/products/catlink-baymax.jpg', TRUE);

-- =========================
-- 6. CARTS (Giỏ hàng riêng cho từng user)
-- =========================
INSERT INTO carts (user_id)
VALUES 
    (1),
    (2),
    (3),
    (4);

-- =========================
-- 7. CART ITEMS (Sản phẩm đang nằm trong giỏ)
-- =========================
INSERT INTO cart_items (cart_id, product_id, quantity)
VALUES 
    (3, 3, 2), -- User 3 (An) có 2 túi hạt Royal Canin trong giỏ
    (3, 5, 1), -- User 3 (An) có 1 cây cào móng
    (4, 1, 1), -- User 4 (Bích) có 1 máy cho ăn PETKIT
    (4, 3, 1), -- User 4 (Bích) có 1 túi hạt Royal Canin
    (1, 2, 1); -- Admin 1 thử cho 1 máy sấy vào giỏ

-- =========================
-- 8. ORDERS (Đơn hàng đã đặt)
-- =========================
INSERT INTO orders (user_id, address_id, total_price, payment_method, payment_status, status, note)
VALUES 
    (3, 1, 1450000.00, 'COD', 'UNPAID', 'PENDING', 'Giao giờ hành chính giúp mình'),
    (3, 2, 7270000.00, 'BANK_TRANSFER', 'PAID', 'DELIVERED', 'Gọi trước khi giao 15 phút'),
    (4, 3, 4200000.00, 'CREDIT_CARD', 'PAID', 'SHIPPING', 'Hàng dễ vỡ xin nhẹ tay'),
    (4, 4, 575000.00, 'COD', 'UNPAID', 'COMPLETED', ''),
    (3, 1, 380000.00, 'MOMO', 'PAID', 'CANCELLED', 'Khách đổi ý muốn đổi dòng hạt khác');

-- =========================
-- 9. ORDER ITEMS (Chi tiết sản phẩm trong đơn)
-- =========================
INSERT INTO order_items (order_id, product_id, price, quantity, subtotal)
VALUES 
    -- Đơn hàng 1 (Total: 1,450,000)
    (1, 1, 1450000.00, 1, 1450000.00),
    
    -- Đơn hàng 2 (Total: 7,270,000 = 6,890,000 + 380,000)
    (2, 2, 6890000.00, 1, 6890000.00),
    (2, 3, 380000.00, 1, 380000.00),
    
    -- Đơn hàng 3 (Total: 4,200,000)
    (3, 4, 4200000.00, 1, 4200000.00),
    
    -- Đơn hàng 4 (Total: 575,000 = 380,000 + 195,000)
    (4, 3, 380000.00, 1, 380000.00),
    (4, 5, 195000.00, 1, 195000.00);

-- =========================
-- 10. REVIEWS (Đánh giá từ khách đã mua)
-- =========================
INSERT INTO reviews (user_id, product_id, rating, comment)
VALUES 
    (3, 2, 5, 'Lồng sấy dùng rất êm, mèo nhà mình không bị hoảng sợ. Sấy khoảng 45 phút là khô đét!'),
    (3, 3, 4, 'Mèo ăn hợp hợp vặt, lông mượt hơn rõ rệt nhưng hạt hơi nhỏ đối với mèo to.'),
    (4, 4, 5, 'Máy dọn phân cực kỳ đáng tiền, không còn mùi hôi trong phòng nữa. App PETKIT dễ dùng.'),
    (4, 5, 4, 'Bìa cào móng chắc chắn, mèo nhà mình rất thích nằm lên đây ngủ.'),
    (4, 3, 5, 'Giao hàng nhanh, đóng gói cẩn thận, date hạt còn rất xa.');