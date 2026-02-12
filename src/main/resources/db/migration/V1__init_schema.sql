CREATE TABLE tenants (
                         id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
                         created_at DATETIME2 NOT NULL,
                         name VARCHAR(255) NOT NULL UNIQUE,
                         slug VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE users (
                       id UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
                       created_at DATETIME2 NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       name VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL CHECK (role IN ('OWNER','MEMBER')),
                       tenant_id UNIQUEIDENTIFIER NOT NULL,
                       CONSTRAINT FK_users_tenant FOREIGN KEY (tenant_id)
                           REFERENCES tenants(id)
);
