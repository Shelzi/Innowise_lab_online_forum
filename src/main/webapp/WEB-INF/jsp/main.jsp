<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Онлайн Форум</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 56px; /* Для корректного отображения фиксированной Navbar */
        }

        .banner {
            background-color: #f8f9fa;
            padding: 60px 0;
            text-align: center;
        }

        .footer {
            background-color: #343a40;
            color: white;
            padding: 20px 0;
            margin-top: 40px;
        }

        .category-card {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Онлайн Форум</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Переключить навигацию">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Главная</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Категории</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">О нас</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Контакты</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Регистрация</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/login">Вход</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="banner">
    <div class="container">
        <h1 class="display-4">Добро пожаловать на Онлайн Форум!</h1>
        <p class="lead">Обсуждайте различные темы, делитесь знаниями и общайтесь с единомышленниками.</p>
        <a href="#" class="btn btn-primary btn-lg">Начать сейчас</a>
    </div>
</div>

<!-- Список категорий форума -->
<div class="container my-5">
    <h2 class="mb-4">Категории</h2>
    <div class="row">
        <!-- Категория 1 -->
        <div class="col-md-4">
            <div class="card category-card">
                <div class="card-body">
                    <h5 class="card-title">Технологии</h5>
                    <p class="card-text">Обсуждение новейших технологий, программирования и IT-индустрии.</p>
                    <a href="#" class="btn btn-primary">Перейти</a>
                </div>
            </div>
        </div>
        <!-- Категория 2 -->
        <div class="col-md-4">
            <div class="card category-card">
                <div class="card-body">
                    <h5 class="card-title">Путешествия</h5>
                    <p class="card-text">Делитесь впечатлениями о поездках, советами и рекомендациями.</p>
                    <a href="#" class="btn btn-primary">Перейти</a>
                </div>
            </div>
        </div>
        <!-- Категория 3 -->
        <div class="col-md-4">
            <div class="card category-card">
                <div class="card-body">
                    <h5 class="card-title">Спорт</h5>
                    <p class="card-text">Обсуждение различных видов спорта, событий и достижений.</p>
                    <a href="#" class="btn btn-primary">Перейти</a>
                </div>
            </div>
        </div>
    </div>
</div>

<footer class="footer">
    <div class="container text-center">
        <p>&copy; 2024 Онлайн Форум. Все права защищены.</p>
        <p>
            <a href="#" class="text-white me-2">Политика конфиденциальности</a>
            |
            <a href="#" class="text-white ms-2">Условия использования</a>
        </p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
