# Сортировка слиянием нескольких файлов
<h2>Описание</h2>
<p>Программа получает на вход файлы, содержащие данные одного из двух видов: целые числа или строки. Данные записаны
в столбик (каждая строка файла – новый элемент). Строки могут содержать любые не пробельные
символы, строки с пробелами считаются ошибочными. Файлы предварительно отсортированы по возрастанию.</p>
<p>Результатом работы программы являться новый файл с объединенным содержимым
входных файлов, отсортированным по возрастанию или убыванию путем сортировки слиянием.</p>
<p>Если входные файлы содержат не корректные данные (нарушен порядок сортировки в файле, строки содержат пробелы, при целочисленной сортировке невозможно привести строку к числу), будет произведена частичная сортировка. Не корректные строки будут игнорироваться.
</p>

<h2>Использованные средства</h2>
<p><a href="https://www.oracle.com/java/technologies/javase-jdk15-downloads.html" target="_blank">Open JDK 17</a> - компилятор\интерпритатор</p>
<p><a href="https://maven.apache.org/index.html" target="_blank">Maven 3.8.5</a> - сборка и управление проектом</p>
<p><a href="https://mvnrepository.com/artifact/junit/junit/4.13" target="_blank">JUnit 4.13.2</a> - модульное тестирование</p>
<p><a href="https://mvnrepository.com/artifact/commons-io/commons-io/2.11.0" target="_blank">Apache Commons IO 2.11.0</a> - библиотека для работы с потоками ввода-вывода</p>
<p><a href="https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-checkstyle-plugin" target="_blank">Apache Maven Checkstyle Plugin 3.2.0</a> - автоматическая инспекция кодa</p>
<p><a href="https://maven.apache.org/plugins/maven-shade-plugin/" target="_blank">Apache Maven Shade Plugin 3.0.0</a> - упаковка проекта со всеми необходимыми зависимостями в jar-архив</p>

<h2>Компиляция</h2>
<pre>
<code>$ cd cft_test
$ mvn package</code>
</pre>
В папке проекта появится папка target, a в ней файл cft_test.jar

<h2>Запуск</h2>
<pre>
<code>$ cd target
$ java -jar cft_test.jar -i -a out.txt in1.txt in2.txt in3.txt</code>
</pre>
<p>Параметры программы задаются при запуске через аргументы командной строки, по порядку:</p>
<ol>
    <li>-i - тип данных (-s или -i), обязательный;</li>
    <li>-a - режим сортировки (-a - по возрастанию или -d - по убыванию), необязательный, по умолчанию сортируем по возрастанию;</li>
    <li>out.txt - путь до выходного файла (если файла по заданному пути не существует, то будет создан файл с указанным именем), обязательный;</li>
    <li>in1.txt и т. д. - путь до входных существующих файлов (если файла по заданному пути не существует, то программа завершится с ошибкой), не менее одного.</li>
</ol>

<h3>Пример запуска программы в ОС Windows 10</h3>
<pre>
<code>$ cd target
$ java -jar cft_test.jar -s -d C:\Users\user\Desktop\out.txt C:\Users\user\Desktop\in1.txt C:\Users\user\Desktop\in2.txt</code>
</pre>

