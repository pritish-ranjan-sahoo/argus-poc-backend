-- 1. Clean existing records safely (Prevents crashes if tables are empty or fresh)
TRUNCATE TABLE address, app_user RESTART IDENTITY CASCADE^^

-- 2. Execute block using inner semicolons safely
DO $$
DECLARE
admin_id UUID := gen_random_uuid();
    customer_1_id UUID := gen_random_uuid();
    customer_2_id UUID := gen_random_uuid();
    customer_3_id UUID := gen_random_uuid();
    customer_4_id UUID := gen_random_uuid();
BEGIN

    -- 3. Insert Dummy AppUsers
INSERT INTO app_user (id, username, email, password, role, is_active, created_at)
VALUES
    (admin_id, 'admin_user', 'admin@ecommerce.com', 'superSecretPwd123', 'ADMIN', true, CURRENT_TIMESTAMP),
    (customer_1_id, 'john_doe', 'john.doe@example.com', 'securePass2026', 'CUSTOMER', true, CURRENT_TIMESTAMP),
    (customer_2_id, 'jane_smith', 'jane.smith@example.com', 'smithPassword99', 'CUSTOMER', true, CURRENT_TIMESTAMP),
    (customer_3_id, 'alex_jones', 'alex.j@example.com', 'alexSecure777', 'CUSTOMER', true, CURRENT_TIMESTAMP),
    (customer_4_id, 'emily_clark', 'emily.c@example.com', 'emilyPassWord55', 'CUSTOMER', false, CURRENT_TIMESTAMP);

-- 4. Insert Addresses mapping accurately to the generated customer UUIDs
INSERT INTO address (address_id, line_1, line_2, line_3, city, state, zip_code, is_active, customer_id, created_at)
VALUES
    -- Admin corporate address
    (gen_random_uuid(), '789 Headquarters Blvd', 'Floor 12', 'Corporate Business District', 'Austin', 'Texas', '73301C', true, admin_id, CURRENT_TIMESTAMP),

    -- Customer 1 (John Doe): 2 Addresses (Home & Work)
    (gen_random_uuid(), '123 Main Street', 'Apartment 4B', 'Near Central Park', 'New York', 'New York', '10001A', true, customer_1_id, CURRENT_TIMESTAMP),
    (gen_random_uuid(), '555 Corporate Plaza', 'Suite 900', 'Tech Park District', 'New York', 'New York', '10022B', false, customer_1_id, CURRENT_TIMESTAMP),

    -- Customer 2 (Jane Smith): 3 Addresses (Home, Vacation, Parents)
    (gen_random_uuid(), '456 Oak Avenue', 'Suite 100', 'Industrial Zone Phase 1', 'Los Angeles', 'California', '90001B', true, customer_2_id, CURRENT_TIMESTAMP),
    (gen_random_uuid(), '777 Ocean Drive', 'Beach House', 'Near Shoreline Boardwalk', 'Miami', 'Florida', '33101A', true, customer_2_id, CURRENT_TIMESTAMP),
    (gen_random_uuid(), '888 Maple Lane', 'Subdivision B', 'Near Community Hospital', 'Chicago', 'Illinois', '60601C', false, customer_2_id, CURRENT_TIMESTAMP),

    -- Customer 3 (Alex Jones): 2 Addresses (Primary Shipping & Alternate Billing)
    (gen_random_uuid(), '101 Pine Road', 'Block G', 'Green Valley Residences', 'Seattle', 'Washington', '98101X', true, customer_3_id, CURRENT_TIMESTAMP),
    (gen_random_uuid(), '202 Cedar Street', 'PO Box 450', 'Main Postal Depot Box', 'Seattle', 'Washington', '98105Y', false, customer_3_id, CURRENT_TIMESTAMP),

    -- Customer 4 (Emily Clark): 1 Address (Inactive User Account)
    (gen_random_uuid(), '303 Elm Boulevard', 'Apartment 12', 'West End Residential Area', 'Denver', 'Colorado', '80201Z', true, customer_4_id, CURRENT_TIMESTAMP);

END $$;
^^
