-- -----------------------------------------------------
-- Data for table `todos`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `todos`;
INSERT INTO `todos`.`users` (`user_id`, `user_name`, `email`, `full_name`, `password`) VALUES (1, 'user01', 'user01@mail.com', 'User 01', NULL);
INSERT INTO `todos`.`users` (`user_id`, `user_name`, `email`, `full_name`, `password`) VALUES (2, 'user02', 'user02@mail.com', 'User 02', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `todos`.`tasks`
-- -----------------------------------------------------
START TRANSACTION;
USE `todos`;
INSERT INTO `todos`.`tasks` (`task_id`, `user_id`, `task`, `description`) VALUES (1, 1, 'Task 01', 'Description of Task 01');
INSERT INTO `todos`.`tasks` (`task_id`, `user_id`, `task`, `description`) VALUES (2, 1, 'Task 02', 'Description of Task 02');
INSERT INTO `todos`.`tasks` (`task_id`, `user_id`, `task`, `description`) VALUES (3, 2, 'Task 01', 'Description of Task 01');
INSERT INTO `todos`.`tasks` (`task_id`, `user_id`, `task`, `description`) VALUES (4, 2, 'Task 02', 'Description of Task 02');
INSERT INTO `todos`.`tasks` (`task_id`, `user_id`, `task`, `description`) VALUES (5, 2, 'Task 03', 'Description of Task 03');

COMMIT;