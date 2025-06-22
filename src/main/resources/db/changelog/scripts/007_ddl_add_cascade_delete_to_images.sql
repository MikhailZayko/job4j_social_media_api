ALTER TABLE images DROP CONSTRAINT images_post_id_fkey;
ALTER TABLE images
ADD CONSTRAINT images_post_id_fkey
FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE;