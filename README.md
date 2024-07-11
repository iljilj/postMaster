# PostMaster

## Оглавление
1. [Краткое описание](#краткое-описание)
2. [Функциональные Возможности](#функциональные-возможности)
3. [Используемые Технологии](#используемые-технологии)
4. [Скриншоты](#скриншоты)

## Краткое описание
Веб-приложение для управления постами. Включает функции CRUD, возможности администрирования для управления пользователями и их ролями, валидацию паролей, имен пользователей и т.д..

## Функциональные Возможности
- **Функции пользователя:**
    - Регистрация и вход в систему
    - Создание, редактирование и удаление личных постов
    - Просмотр всех постов в feed
    - Персональная страница с постами пользователя
    - Смена пароля
    - Удаление аккаунта
    - Запрет на создание постов для заблокированных пользователей

- **Функции администратора:**
    - Панель администратора для управления пользователями 
    - Удаление пользователей 
    - Блокировка и разблокировка пользователей 
    - Назначение админских прав пользователям 
    - Создание новых пользователей 
    - Удаление постов любого пользователя из ленты или с персональных страниц

## Используемые Технологии
- Spring Boot
- Spring Security
- Thymeleaf
- Bootstrap
- MySQL
- Lombok

## Скриншоты
- **Log in:**
  ![Log in](https://github.com/iljilj/postMaster/assets/79352449/c11bab1c-7340-4f59-a6a6-71cba7b766d3)

- **Неудачный log in:**
  ![Failed log in](https://github.com/iljilj/postMaster/assets/79352449/b4326c21-703e-40d2-847b-e5584bec9f85)

- **Попытка регистрации с пустым паролем:**
  ![Failed sign up - password](https://github.com/iljilj/postMaster/assets/79352449/65386742-d0c1-4b1d-961c-68a57d9e97f0)

- **Попытка регистрации с существующим именем пользователя:**
  ![Failed sign up - username](https://github.com/iljilj/postMaster/assets/79352449/5815b268-7d9c-4900-90bb-cd2e0af8b2f2)

- **Админка:**
  ![Admin panel](https://github.com/iljilj/postMaster/assets/79352449/84917a47-1cbc-41d0-bd1b-1374583848a5)

- **Админка - пользователи с админскими правами:**
  ![Screenshot from 2024-07-11 17-28-03](https://github.com/iljilj/postMaster/assets/79352449/a0729788-c6d5-4d8d-8598-108705c2e1f1)
  
- **Админка - забаненные пользователи:**
  ![Screenshot from 2024-07-11 17-28-06](https://github.com/iljilj/postMaster/assets/79352449/6a3637b2-4df6-40d9-b73e-13be09c6f662)

- **Создание нового пользователя из админки:**
![Screenshot from 2024-07-11 17-28-13](https://github.com/iljilj/postMaster/assets/79352449/0d3d70c1-6535-4585-95f6-1bfd2085e495)

- **Личная страница пользователя глазами этого же пользователя**
- Есть возможность создания удаления и редактирования поста, смены пароля, удаления аккаунта
![image](https://github.com/iljilj/postMaster/assets/79352449/affc5629-bdc1-4037-b424-25d4522d9f8d)


- **Личная страница пользователя глазами админа**
- Есть возможность удаления поста, бана аккаунта
![Screenshot from 2024-07-11 17-34-45](https://github.com/iljilj/postMaster/assets/79352449/a7ebbc89-362a-4988-ad70-33a0e8eeab49)

- **Личная страница пользователя другого пользователя**
- Можно только смотреть
![image](https://github.com/iljilj/postMaster/assets/79352449/271e3120-3364-48dc-b52b-de3703a31962)



- **Feed глазами пользователя без админских прав:**
- Можно удалять только свои посты
![image](https://github.com/iljilj/postMaster/assets/79352449/7be61f68-daa0-4ae6-96ba-fbafac331116)


- **Feed глазами пользователя админа:**
- Можно удалять любые посты
![image](https://github.com/iljilj/postMaster/assets/79352449/f555f5a1-2b8f-4b38-9488-b898216b4be8)



- **Смена пароля:**
![Screenshot from 2024-07-11 17-34-02](https://github.com/iljilj/postMaster/assets/79352449/669daa8c-c90c-4648-9001-13c508926112)
- **Удалние своего аккаунта:**
![Screenshot from 2024-07-11 17-34-07](https://github.com/iljilj/postMaster/assets/79352449/37fff59b-a742-434b-9fe3-0ee788c638d8)
- **Забаненный пользователь не может оставлять посты:**
![Screenshot from 2024-07-11 17-35-30](https://github.com/iljilj/postMaster/assets/79352449/96bff707-426b-489e-8edc-63a4349aadf8)



