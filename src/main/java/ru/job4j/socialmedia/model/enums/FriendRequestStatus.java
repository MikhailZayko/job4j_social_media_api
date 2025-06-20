package ru.job4j.socialmedia.model.enums;

public enum FriendRequestStatus {
    /**
     * Запрос отправлен, ожидает ответа
     */
    PENDING,

    /**
     * Запрос принят получателем
     */
    ACCEPTED,

    /**
     * Запрос отклонен получателем
     */
    DECLINED,
}
