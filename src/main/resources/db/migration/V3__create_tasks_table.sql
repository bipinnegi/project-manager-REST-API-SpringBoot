CREATE TABLE tasks (
                       id UNIQUEIDENTIFIER PRIMARY KEY,
                       title NVARCHAR(255) NOT NULL,
                       description NVARCHAR(1000),
                       status NVARCHAR(50),
                       project_id UNIQUEIDENTIFIER NOT NULL,
                       created_at DATETIME2,
                       updated_at DATETIME2,

                       CONSTRAINT fk_task_project
                           FOREIGN KEY (project_id)
                               REFERENCES projects(id)
);