package com.wmano;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        File rootFolder = new File("./src/main/java/Root");

        for (File f : Objects.requireNonNull(rootFolder.listFiles())){
            String name = f.getName();
            if (name.endsWith("_html") && f.isDirectory()){
                StringBuilder htmlElement = new StringBuilder();
                htmlElement.append(createHtmlElement(f, ""));
                System.out.println(htmlElement);
                String htmlTemplate = createHtmlCode(htmlElement);
                Path outputFile = Paths.get(name.replace("_", "."));
                Files.writeString(outputFile, htmlTemplate);
                System.out.println("Fertig mit datei: " + name);
            }
        }

    }

    private static String createHtmlCode(StringBuilder htmlElement) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Gebete</title>
                    <style>
                        body {
                            max-width: 900px;
                            margin: auto;
                            font-size: 35px;
                            font-family: Arial, sans-serif;
                            line-height: 150%;
                            transition: background-color 0.5s, color 0.5s;
                            background-color: white;
                            color: black;
                        }
                        h1, h2, h3 {
                            text-align: center;
                            font-family: Garamond, serif;
                            cursor: pointer;
                        }
                        p {
                            text-align: center;
                            font-family: Verdana, sans-serif;
                            display: none;
                            transition: display 0.5s ease-in-out;
                            font-size: 45px;
                            line-height: 150%;
                        }
                        p.sichtbar {
                            display: block;
                        }
                        hr.erster {
                            border: 2px solid #333;
                            margin: 20px 0;
                        }
                        hr.zweiter {
                            border: 1px solid #333;
                            margin: 20px 0;
                        }
                        .top-bar {
                            width: 100%;
                            height: 200px;
                            background-color: #121212;
                            color: white;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            margin-bottom: 20px;
                        }
                        .toggle-button {
                            background-color: transparent;
                            border: 1px solid white;
                            color: white;
                            padding: 20px 40px;
                            cursor: pointer;
                            font-size: 46px;
                            transition: background-color 0.3s, color 0.3s;
                        }
                        .toggle-button:hover {
                            background-color: white;
                            color: #121212;
                        }
                        body.dark-mode {
                            background-color: #121212;
                            color: white;
                        }
                    </style>
                    <script>
                        document.addEventListener('DOMContentLoaded', function() {
                            var aktuelleZeitMillis = Date.now();
                            var differenzTage = Math.floor(aktuelleZeitMillis / 86400000);
                            var psalmNumberToday = (differenzTage % 20) + 1;
                            document.getElementById('psalmNumberToday').textContent = psalmNumberToday;
                
                            document.getElementById('colorToggleButton').addEventListener('click', function() {
                                document.body.classList.toggle('dark-mode');
                            });
                        });
                
                        function toggleText(id) {
                            var text = document.getElementById(id);
                            if (text.style.display === 'none') {
                                text.style.display = 'block';
                            } else {
                                text.style.display = 'none';
                            }
                        }
                    </script>
                </head>
                <body>
                    <div class="top-bar">
                        <button id="colorToggleButton" class="toggle-button">Toggle Color</button>
                    </div>
        """ + htmlElement +
        """
                </body>
                </html>
        """;
    }

    private static String createHtmlElement(File f, String title) throws IOException {
        StringBuilder htmlElement = new StringBuilder();
        if(!title.isEmpty()) {
            String beautifiedTitle = title.replace(".html", "")
                    .replace("_", " ")
                    .replaceAll("\\(\\d+\\)", "");
            htmlElement.append("<h2 class=\"toggle-title\">").append(beautifiedTitle).append("</h2>\n");
            htmlElement.append("<div class=\"content\">\n");
            if (f.isDirectory()) {
                for (File f_ : Objects.requireNonNull(f.listFiles())) {
                    htmlElement.append(createHtmlElement(f_, f_.getName()));
                }
            } else {
                htmlElement.append(Files.readString(f.toPath()));
                htmlElement.append("\n");
            }
            htmlElement.append("<div>\n");
            htmlElement.append("<hr />\n");
        } else {
            if (f.isDirectory()) {
                for (File f_ : Objects.requireNonNull(f.listFiles())) {
                    htmlElement.append(createHtmlElement(f_, f_.getName()));
                }
            }
        }
        return htmlElement.toString();
    }



}