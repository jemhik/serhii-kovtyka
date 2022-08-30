--Data for table `training_center`.`roles`

INSERT INTO `training_center`.`roles`
    (`role_id`, `name`)
VALUES (1, 'admin');
INSERT INTO `training_center`.`roles`
    (`role_id`, `name`)
VALUES (2, 'teacher');
INSERT INTO `training_center`.`roles`
    (`role_id`, `name`)
VALUES (3, 'student');

--Data for table `training_center`.`status`

INSERT INTO `training_center`.`status`
    (`status_id`, `name`)
VALUES (1, "active");
INSERT INTO `training_center`.`status`
    (`status_id`, `name`)
VALUES (2, "blocked");