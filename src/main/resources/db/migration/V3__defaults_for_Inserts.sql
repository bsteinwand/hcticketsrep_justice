Alter table hctickets.ticketassignment alter column ActiveDate set Default CURRENT_TIMESTAMP;

Alter table hctickets.task add Priority varchar(25);

Alter table hctickets.task alter column CreationDate set Default CURRENT_TIMESTAMP;

Alter table hctickets.task add DueDate timestamptz;