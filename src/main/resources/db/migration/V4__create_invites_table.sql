CREATE TABLE invites (
                         id UNIQUEIDENTIFIER PRIMARY KEY,
                         email NVARCHAR(255) NOT NULL UNIQUE,
                         token NVARCHAR(255) NOT NULL UNIQUE,
                         expires_at DATETIME2 NOT NULL,
                         accepted BIT NOT NULL,
                         tenant_id UNIQUEIDENTIFIER NOT NULL,
                         created_at DATETIME2 NOT NULL,

                         CONSTRAINT fk_invite_tenant FOREIGN KEY (tenant_id)
                             REFERENCES tenants(id)
);