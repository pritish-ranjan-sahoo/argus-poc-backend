-- 1. Clean existing records safely (Prevents crashes if tables are empty or fresh)
TRUNCATE TABLE order_item, orders, cart_item, cart, products, address, app_user RESTART IDENTITY CASCADE^^

-- 2. Execute block using inner semicolons safely
DO $$
DECLARE
admin_id UUID := gen_random_uuid();
    seller_1_id UUID := gen_random_uuid();
    customer_1_id UUID := gen_random_uuid();
    customer_2_id UUID := gen_random_uuid();
    customer_3_id UUID := gen_random_uuid();
    customer_4_id UUID := gen_random_uuid();

    address_1_id UUID := gen_random_uuid(); -- admin HQ
    address_2_id UUID := gen_random_uuid(); -- seller warehouse
    address_3_id UUID := gen_random_uuid(); -- john home
    address_4_id UUID := gen_random_uuid(); -- john work
    address_5_id UUID := gen_random_uuid(); -- jane home
    address_6_id UUID := gen_random_uuid(); -- jane vacation
    address_7_id UUID := gen_random_uuid(); -- jane parents
    address_8_id UUID := gen_random_uuid(); -- alex primary
    address_9_id UUID := gen_random_uuid(); -- alex alternate
    address_10_id UUID := gen_random_uuid(); -- emily

    product_1_id UUID := gen_random_uuid();
    product_2_id UUID := gen_random_uuid();
    product_3_id UUID := gen_random_uuid();
    product_4_id UUID := gen_random_uuid();
    product_5_id UUID := gen_random_uuid();
    product_6_id UUID := gen_random_uuid();

    cart_1_id UUID := gen_random_uuid(); -- john
    cart_2_id UUID := gen_random_uuid(); -- jane
    cart_3_id UUID := gen_random_uuid(); -- alex
    cart_4_id UUID := gen_random_uuid(); -- emily

    order_1_id UUID := gen_random_uuid(); -- john, delivered
    order_2_id UUID := gen_random_uuid(); -- john, pending
    order_3_id UUID := gen_random_uuid(); -- jane, shipped
    order_4_id UUID := gen_random_uuid(); -- alex, confirmed

BEGIN

    -- 3. Insert Dummy AppUsers
INSERT INTO app_user (id, username, email, password, role, is_active, created_at)
VALUES
    (admin_id, 'admin_user', 'admin@ecommerce.com', 'superSecretPwd123', 'ADMIN', true, CURRENT_TIMESTAMP),
    (seller_1_id, 'techbazaar_store', 'contact@techbazaar.com', 'sellerPass2026', 'SELLER', true, CURRENT_TIMESTAMP),
    (customer_1_id, 'john_doe', 'john.doe@example.com', 'securePass2026', 'CUSTOMER', true, CURRENT_TIMESTAMP),
    (customer_2_id, 'jane_smith', 'jane.smith@example.com', 'smithPassword99', 'CUSTOMER', true, CURRENT_TIMESTAMP),
    (customer_3_id, 'alex_jones', 'alex.j@example.com', 'alexSecure777', 'CUSTOMER', true, CURRENT_TIMESTAMP),
    (customer_4_id, 'emily_clark', 'emily.c@example.com', 'emilyPassWord55', 'CUSTOMER', false, CURRENT_TIMESTAMP);

-- 4. Insert Addresses mapping accurately to the generated user UUIDs
INSERT INTO address (address_id, line_1, line_2, line_3, city, state, zip_code, is_active, customer_id, created_at)
VALUES
    (address_1_id, '789 Headquarters Blvd', 'Floor 12', 'Corporate Business District', 'Austin', 'Texas', '73301C', true, admin_id, CURRENT_TIMESTAMP),
    (address_2_id, '42 Distribution Way', 'Warehouse B', 'Industrial Logistics Park', 'Newark', 'New Jersey', '07102D', true, seller_1_id, CURRENT_TIMESTAMP),
    (address_3_id, '123 Main Street', 'Apartment 4B', 'Near Central Park', 'New York', 'New York', '10001A', true, customer_1_id, CURRENT_TIMESTAMP),
    (address_4_id, '555 Corporate Plaza', 'Suite 900', 'Tech Park District', 'New York', 'New York', '10022B', false, customer_1_id, CURRENT_TIMESTAMP),
    (address_5_id, '456 Oak Avenue', 'Suite 100', 'Industrial Zone Phase 1', 'Los Angeles', 'California', '90001B', true, customer_2_id, CURRENT_TIMESTAMP),
    (address_6_id, '777 Ocean Drive', 'Beach House', 'Near Shoreline Boardwalk', 'Miami', 'Florida', '33101A', true, customer_2_id, CURRENT_TIMESTAMP),
    (address_7_id, '888 Maple Lane', 'Subdivision B', 'Near Community Hospital', 'Chicago', 'Illinois', '60601C', false, customer_2_id, CURRENT_TIMESTAMP),
    (address_8_id, '101 Pine Road', 'Block G', 'Green Valley Residences', 'Seattle', 'Washington', '98101X', true, customer_3_id, CURRENT_TIMESTAMP),
    (address_9_id, '202 Cedar Street', 'PO Box 450', 'Main Postal Depot Box', 'Seattle', 'Washington', '98105Y', false, customer_3_id, CURRENT_TIMESTAMP),
    (address_10_id, '303 Elm Boulevard', 'Apartment 12', 'West End Residential Area', 'Denver', 'Colorado', '80201Z', true, customer_4_id, CURRENT_TIMESTAMP);

-- 5. Insert Products (seller_id references seller/admin users)
INSERT INTO products (product_id, name, description, price_per_unit, stock, category_type, created_at, updated_at, seller_id, product_image_url)
VALUES
    (product_1_id, 'Wireless Mechanical Keyboard', 'Compact 75% mechanical keyboard with hot-swappable switches', 89.99, 150, 'ELECTRONICS_TECHNOLOGY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, seller_1_id, 'https://example.com/images/keyboard.jpg'),
    (product_2_id, 'Noise Cancelling Headphones', 'Over-ear headphones with 30hr battery life and ANC', 149.50, 80, 'ELECTRONICS_TECHNOLOGY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, seller_1_id, 'https://example.com/images/headphones.jpg'),
    (product_3_id, 'Organic Cotton T-Shirt', 'Breathable everyday t-shirt, available in multiple colors', 19.99, 300, 'FASHION_APPAREL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, seller_1_id, 'https://example.com/images/tshirt.jpg'),
    (product_4_id, 'Stainless Steel Water Bottle', 'Insulated 1L bottle, keeps drinks cold for 24 hours', 24.99, 200, 'HOME_LIVING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, seller_1_id, 'https://example.com/images/bottle.jpg'),
    (product_5_id, 'Vitamin C Face Serum', 'Brightening serum with hyaluronic acid, 30ml bottle', 16.99, 250, 'HEALTH_BEAUTY_PERSONAL_CARE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, admin_id, 'https://example.com/images/serum.jpg'),
    (product_6_id, 'Organic Basmati Rice 5kg', 'Premium long-grain basmati rice, aged for extra aroma', 12.99, 500, 'ESSENTIALS_FOOD_GROCERY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, seller_1_id, 'https://example.com/images/rice.jpg');

-- 6. Insert Carts (one per active customer)
INSERT INTO cart (cart_id, user_id)
VALUES
    (cart_1_id, customer_1_id),
    (cart_2_id, customer_2_id),
    (cart_3_id, customer_3_id),
    (cart_4_id, customer_4_id);

-- 7. Insert Cart Items
INSERT INTO cart_item (cart_item_id, cart_id, product_id, quantity)
VALUES
    (gen_random_uuid(), cart_1_id, product_2_id, 1),
    (gen_random_uuid(), cart_1_id, product_4_id, 2),
    (gen_random_uuid(), cart_2_id, product_5_id, 1),
    (gen_random_uuid(), cart_2_id, product_3_id, 3),
    (gen_random_uuid(), cart_3_id, product_1_id, 1),
    (gen_random_uuid(), cart_4_id, product_6_id, 1);

-- 8. Insert Orders (address should belong to the same customer)
INSERT INTO orders (order_id, customer_id, status, payment_method, address_id)
VALUES
    (order_1_id, customer_1_id, 'DELIVERED', 'CARD', address_3_id),
    (order_2_id, customer_1_id, 'PENDING', 'CASH', address_3_id),
    (order_3_id, customer_2_id, 'SHIPPED', 'UPI', address_5_id),
    (order_4_id, customer_3_id, 'CONFIRMED', 'NET_BANKING', address_8_id);

-- 9. Insert Order Items (price is a snapshot at time of purchase)
INSERT INTO order_item (order_item_id, order_id, product_id, quantity, price)
VALUES
    (gen_random_uuid(), order_1_id, product_1_id, 1, 89.99),
    (gen_random_uuid(), order_1_id, product_5_id, 2, 16.99),
    (gen_random_uuid(), order_2_id, product_3_id, 3, 19.99),
    (gen_random_uuid(), order_3_id, product_2_id, 1, 149.50),
    (gen_random_uuid(), order_3_id, product_6_id, 1, 12.99),
    (gen_random_uuid(), order_4_id, product_4_id, 2, 24.99);

END $$;
^^