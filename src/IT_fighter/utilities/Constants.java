package IT_fighter.utilities;

/**
 * verwaltet wichtige Konstanten der Spielfigur
 */
public class Constants {
    /**
     * verwaltet Konstanten für Aktionen der Spielfigur
     */
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int IDLE_WITHOUT_EYES = 1;
        public static final int GOING = 2;
        public static final int RUNING = 3;
        public static final int CRAWLING = 4;
        public static final int JUMPING = 5;
        public static final int DESPAWN = 6;

        public static final int HIT = 7;
        public static final int ATTACK = 8;

        /**
         * gibt zurück wie viele Bilder eine Animation besitzt
         * @param action gibt an, um welche Animation es sich handelt.
         * @return anzahl der Bilder, die für die Animation hinterlegt sind.
         */
        public static int getSpritesPerLine(int action) {
            switch (action) {
                case IDLE:
                case IDLE_WITHOUT_EYES:
                    return 2;
                case GOING:
                case DESPAWN:
                    return 4;
                case CRAWLING:
                    return 6;
                case RUNING:
                case JUMPING:
                case HIT:
                case ATTACK:
                    return 8;
                default:
                    return 1;

            }
        }

    }

}
