-- Create Database
CREATE DATABASE banking_db OWNER postgres ENCODING UTF8;
GRANT ALL PRIVILEGES ON DATABASE  banking_db TO postgres;

-- Initial data for roles and permissions
INSERT INTO role (name, description)
VALUES ('ADMIN', 'System administrator with full access'),
       ('MANAGER', 'Manager with supervisory access'),
       ('USER', 'User with basic banking operations');

INSERT INTO permission (name, description, category)
VALUES ('CREATE_USER', 'Create new user accounts', 'USER_MANAGEMENT'),
       ('MANAGE_USER', 'Edit and deactivate user accounts', 'USER_MANAGEMENT'),
       ('CREATE_ACCOUNT', 'Create new bank accounts', 'ACCOUNT_MANAGEMENT'),
       ('VIEW_ALL_ACCOUNTS', 'Create new bank accounts', 'ACCOUNT_MANAGEMENT'),
       ('TRANSFER', 'Process money transfer operations', 'TRANSACTION_MANAGEMENT'),
       ('VIEW_TRANSACTIONS', 'View transaction history', 'TRANSACTION_MANAGEMENT'),
       ('APPROVE_LARGE_TRANSACTIONS', 'Approve high-value transactions', 'TRANSACTION_MANAGEMENT'),
       ('GENERATE_REPORT', 'Generate system reports', 'REPORTING');

--- Role-Permission assignments
--- ADMIN gets all permissions

INSERT INTO role_permission(role_id, permission_id)
SELECT r.id, p.id
FROM role r,
     permission p
WHERE r.name = 'ADMIN';
--- Manager gets most permissions except user creation
INSERT INTO role_permission(role_id, permission_id)
SELECT r.id, p.id
FROM role r,
     permission p
WHERE r.name = 'MANAGER'
  AND p.name NOT IN ('CREATE_USER', 'MANAGE_USER');

--- User gets basic banking permission
INSERT INTO role_permission(role_id, permission_id)
SELECT r.id, p.id
FROM role r,
     permission p
WHERE r.name = 'USER'
  AND p.name IN ('CREATE_ACCOUNT', 'TRANSFER', 'VIEW_TRANSACTIONS');