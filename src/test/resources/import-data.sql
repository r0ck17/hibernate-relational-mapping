INSERT INTO public.course (name) VALUES ('Math');
INSERT INTO public.course (name) VALUES ('Biology');
INSERT INTO public.course (name) VALUES ('Java Enterprise');

INSERT INTO public.trainer (name, surname) VALUES ('Dmitriy', 'Molotov');
INSERT INTO public.trainer (name, surname) VALUES ('Grigoriy', 'Sobolev');

INSERT INTO public.student_profile (performance) VALUES (7);
INSERT INTO public.student_profile (performance) VALUES (6);
INSERT INTO public.student_profile (performance) VALUES (7);
INSERT INTO public.student_profile (performance) VALUES (4.3);
INSERT INTO public.student_profile (performance) VALUES (7.4);

INSERT INTO public.student (course_id, profile_id, education_type, name, surname) VALUES (1, 1, 'FEE_BASED', 'ivan', 'ivanov');
INSERT INTO public.student (course_id, profile_id, education_type, name, surname) VALUES (1, 2, 'FREE', 'nikolay', 'popov');
INSERT INTO public.student (course_id, profile_id, education_type, name, surname) VALUES (2, 3, 'FEE_BASED', 'Sergey', 'Nikolskiy');
INSERT INTO public.student (course_id, profile_id, education_type, name, surname) VALUES (3, 4, 'FREE', 'Andrey', 'Yarnikov');
INSERT INTO public.student (course_id, profile_id, education_type, name, surname) VALUES (1, 5, 'FEE_BASED', 'Roman', 'Romanovich');

INSERT INTO public.courses_trainer (course_id, trainer_id) VALUES (1, 1);
INSERT INTO public.courses_trainer (course_id, trainer_id) VALUES (2, 1);
