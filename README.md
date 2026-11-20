# Aetherium Overlord (Forge 1.20.1)

Мод: мана, 4 пути магии, посохи, броня Архимага, блоки и руды. Зависимости: GeckoLib 4, Curios 5.

## Сборка на GitHub
1. Загрузите ВСЕ файлы этой папки в репозиторий (ветка main), включая скрытую папку `.github`.
2. Откройте вкладку Actions: сборка стартует сама. Готовый .jar — в Artifacts и в Releases.

Если нет `gradlew`, workflow создаёт wrapper сам. Версии библиотек — в `gradle.properties`.
Квесты FTB Quests: `extras/ftbquests/aetherium_overlord.snbt` -> `config/ftbquests/quests/chapters/`.
Текстуры — простые цветные заглушки, замените своими.

Ресурсы (модели, текстуры, рецепты, лут) лежат в `resources_bundle.zip` и распаковываются при сборке (`unzip -n`: ваши файлы в src/main/resources не перезаписываются).
Чтобы заменить текстуру, положите свой PNG по тому же пути в src/main/resources/assets/aetherium_overlord/textures/...
