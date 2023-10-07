## Задание. Hibernate. Relational mapping
1. Сделать 2 таблицы: `student` и `course`
   - на 1 курсе может быть много студентов, один студент на 1 курсе
2. Найти всех студентов которые учатся на курсе _"Java Enterprise"_
3. Добавить таблицу `student_profile` с информацией об успеваемости
4. Удалить всех студентов на курсе _"Java Enterprise"_ со средней оценкой ниже 6
5. Добавить студента к курсу _"Java Enterprise"_
6. Удалить курс _"Java Enterprise"_
7. Добавить таблицу `Trainer`
   - Один тренер может вести несколько курсов, один курс могут проводить разные тренеры
8. Добавить тест, сохраняющий нового тренера и список курсов, которые он ведет
9. Добавить тест для изменения курса
10. Добавить тест для удаления курса