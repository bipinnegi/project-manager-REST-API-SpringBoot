CREATE TABLE projects (
                          id UNIQUEIDENTIFIER PRIMARY KEY,
                          created_at DATETIME2 NOT NULL,
                          name NVARCHAR(255) NOT NULL,
                          description NVARCHAR(1000),
                          tenant_id UNIQUEIDENTIFIER NOT NULL,
                          CONSTRAINT FK_projects_tenant
                              FOREIGN KEY (tenant_id)
                                  REFERENCES tenants(id)
                                  ON DELETE CASCADE
);
