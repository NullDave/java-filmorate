CREATE TABLE IF NOT EXISTS genres (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(255) NOT NULL,
  CONSTRAINT unique_genres_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS mpa_ratings (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(10) NOT NULL,
  description varchar(255),
  CONSTRAINT unique_mpa_ratings_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS films (
  id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  title varchar(255) NOT NULL,
  description varchar(1000),
  release_date date,
  duration integer,
  rating_id bigint REFERENCES mpa_ratings (id)
);

CREATE TABLE IF NOT EXISTS users (
  id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  username varchar(60),
  login varchar(60) NOT NULL,
  email varchar(320) NOT NULL,
  birthday date
);

CREATE TABLE IF NOT EXISTS friends (
  user_id bigint REFERENCES users (id),
  friend_id bigint REFERENCES users (id),
  friend_status boolean DEFAULT FALSE,
  CONSTRAINT unique_friends UNIQUE (user_id,friend_id)
);

CREATE TABLE IF NOT EXISTS film_genres (
  film_id bigint REFERENCES films (id),
  genre_id integer REFERENCES genres (id),
  PRIMARY KEY(film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS film_likes (
  film_id bigint  REFERENCES films (id),
  user_id bigint  REFERENCES users (id),
  PRIMARY KEY(film_id, user_id)
);