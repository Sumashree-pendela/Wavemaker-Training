CREATE TABLE LOGIN (
USER_NAME        VARCHAR(50)      NOT NULL,
PASSWORD         VARCHAR(50)     NOT NULL,
UNIQUE KEY(USER_NAME)
);

CREATE TABLE LEAVE_TYPE(
ID                        INT                NOT NULL AUTO_INCREMENT,
TYPE                      VARCHAR(50)        NOT NULL,
COUNT                     INT                NOT NULL,
GENDER                    VARCHAR(30)        NOT NULL,
PRIMARY KEY(ID)
);

CREATE TABLE EMPLOYEE (
ID                    INT                NOT NULL AUTO_INCREMENT,
NAME                  VARCHAR(50)        NOT NULL,
EMAIL                 VARCHAR(100)       NOT NULL,
DOB                   DATE               NOT NULL,
PHONE_NUMBER          VARCHAR(30)        NOT NULL,
GENDER                VARCHAR(20)        NOT NULL,
USER_NAME             VARCHAR(50)        NOT NULL,
MANAGER_ID            INT                NULL,
PRIMARY KEY(ID),
CONSTRAINT FK_EMPLOYEE_MANAGER_ID  FOREIGN KEY(MANAGER_ID) REFERENCES EMPLOYEE(ID)
);

CREATE TABLE EMPLOYEE_LEAVE(
ID                   INT                 NOT NULL AUTO_INCREMENT,
APPLIED_BY           INT                 NOT NULL,
DATE_FROM            DATE                NOT NULL,
DATE_TO              DATE                NOT NULL,
LEAVE_ID             INT                 NOT NULL,
STATUS               VARCHAR(60)         NOT NULL,
REASON               VARCHAR(100)        NULL,
CREATED_AT           TIMESTAMP           NOT NULL,
PRIMARY KEY(ID),
CONSTRAINT FK_EMPLOYEE_LEAVE_APPLIED_BY FOREIGN KEY(APPLIED_BY) REFERENCES EMPLOYEE(ID),
CONSTRAINT FK_EMPLOYEE_LEAVE_LEAVE_ID FOREIGN KEY(LEAVE_ID) REFERENCES LEAVE_TYPE(ID)
);



