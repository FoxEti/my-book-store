
-- Insert data into the book table


INSERT INTO users (id,email, "password", "role", user_name)
VALUES (1,'etifox9@gmail.com', 'falafel', 'ADMIN', 'eti fox');

INSERT INTO book (image_url, title, author, price, details, category_id, stock_book)
VALUES ('/images/my-daddy-is-the-best.jpg', 'My Daddy Is the Best!', 'Jean Bello', 29.00,
        'Days spent with Dad are the best! In this sweet board book, adorable baby animals express their love for Dad during their day together—from bike rides and baseball games to kite flying and stargazing, the time they share is fun, exciting, and memorable.',
   1, 100);

INSERT INTO book (image_url, title, author, price, details, category_id, stock_book)
VALUES ('/images/rainbow-of-emotions.jpg', 'Rainbow of Emotions', 'Elena Ulyeva', 50.00,
        'Cam, the multicolored chameleon, changes colors with his emotions while visiting jungle animals. He feels angry, shy, sad, and more, ' ||
        'but learns to be himself. ' ,
        1, 100);


INSERT INTO book (image_url, title, author, price, details, category_id, stock_book)
VALUES ('/images/princesses-mermaids-and-unicorns.jpg', 'Princesses, Mermaids & Unicorns', 'Lida Danilova', 27.00,
        'Little learners will enjoy puzzles, activities, games, mazes, doodles, ' ||
        'and more with colorful pages of mermaids, princesses, and fairies. This easy-to-carry book is perfect for kids on the go,' ||
        ' providing hours of fun and a perfect summer travel companion!',
        1, 100);

INSERT INTO book (image_url, title, author, price, details, category_id, stock_book)
VALUES ('/images/THINGS WE HIDE FROM THE LIGHT.jpg', 'THINGS WE HIDE FROM THE LIGHT', 'SCORE, LUCY', 55.00,
        'Police Chief Nash Morgan is recovering from an injury and dealing with a town that views the law as optional.' ||
        ' The last thing he needs is Lina Solavita, his new neighbor, stirring up feelings. ',
        3, 100);

INSERT INTO book (image_url, title, author, price, details, category_id, stock_book)
VALUES ('/images/hari_poter.jpg', 'HARRY POTTER & THE PHILOSOPHER’S STONE N/E', 'ROWLING, J.K.', 64.00,
        'Police Chief Nash Morgan is recovering from an injury and dealing with a town that views the law as optional.' ||
        ' The last thing he needs is Lina Solavita, his new neighbor, stirring up feelings. ',
        2, 100);

INSERT INTO book (image_url, title, author, price, details, category_id, stock_book)
VALUES ('/images/hari_poter2.jpg', 'HARRY POTTER & THE CHAMBER OF SECRETS N/E', 'ROWLING, J.K.', 64.00,
        'Police Chief Nash Morgan is recovering from an injury and dealing with a town that views the law as optional.' ||
        ' The last thing he needs is Lina Solavita, his new neighbor, stirring up feelings. ',
        2, 100);

INSERT INTO book (image_url, title, author, price, details, category_id, stock_book)
VALUES ('/images/the_wave.jpg', 'WAVE N/E', 'RHUE, MORTON', 44.00,
        'Police Chief Nash Morgan is recovering from an injury and dealing with a town that views the law as optional.' ||
        ' The last thing he needs is Lina Solavita, his new neighbor, stirring up feelings. ',
        2, 100);

INSERT INTO book (image_url, title, author, price, details, category_id, stock_book)
VALUES ('/images/women.jpg', 'WOMEN WHO CHANGED THE WORLD', 'RHUE, MORTON', 35.00,
        'L4 WOMEN WHO CHANGED THE WORLD',
        3, 100);

INSERT INTO book (image_url, title, author, price, details, category_id, stock_book)
VALUES ('/images/I WAS BORN FOR THIS.jpg', 'I WAS BORN FOR THIS', 'OSEMAN, ALICE', 53.00,
        'I WAS BORN FOR THIS',
        3, 100);







