package com.bozukyol.badge;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

/**
 * @author Mustafa Ferhan Akman
 * @since 4 May 2014 - 13:59
 */
public class PacmanBadgeTest extends ActivityInstrumentationTestCase2<MainActivity> {
    Context _context;

    public PacmanBadgeTest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        _context = getActivity();
    }

    //badge alacak kadar pothoe yakaladı ama istediğimiz kadar uygulamayı kullanmadı
    //yani yeterince km yapmadı
    public void testCalculatePacmanScenario1() throws Exception {
        PacmanBadge.getInstance().clearPacmanQueue();

        assertEquals(PacmanBadge.INITIAL_DISTANCE, PacmanBadge.getInstance().calculatePacman( 0, 0 ));
        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 0, 0 ));

        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 5f, 3 ));
        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 5f, 3 ));
        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 6f, 25 ));
    }


    //10 km olduğunda 20'den fazla pothole yakalayan adamın senaryosu
    public void testCalculatePacmanScenario2() throws Exception {
        PacmanBadge.getInstance().clearPacmanQueue();

        assertEquals(PacmanBadge.INITIAL_DISTANCE, PacmanBadge.getInstance().calculatePacman( 1f, 1 ));

        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 4f, 7 ));
        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 5f, 15 ));
        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 9f, 19 ));

        assertEquals(PacmanBadge.BADGE_ACHIEVED, PacmanBadge.getInstance().calculatePacman( 12f,21 ));
    }

    // 10 km olduğunda 20'den az pothole yakalayan adamın senaryosu
    public void testCalculatePacmanScenario3() throws Exception {

        PacmanBadge.getInstance().clearPacmanQueue();

        assertEquals(PacmanBadge.INITIAL_DISTANCE, PacmanBadge.getInstance().calculatePacman( 1f, 1 ));

        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 4f, 7 ));
        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 5f, 8 ));
        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 9f, 8 ));

        assertEquals(PacmanBadge.POTHOLES_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 11f, 8 ));
    }

    // User toplamda 19km yol yaptı.
    // ilk 9km'de 8pothole buldu.
    // sonraki 4km'de 11 tane daha buldu. Ara toplamda, 13km'de 19 pothole buldu.
    // sonra bir süre arabayla benzin alırken durdu. Ve aynı datalar aynı gelmeye başladı.
    // ve benzinlikten sonra yoluna devam etti.
    // sonrasında, 3km daha gitti ve 11 pothole daha yakaladı.
    // Toplamda 30 pothole yakalamış oldu.
    // ilk 6km'de yakalamış olduğu 8 tane pothole'u hesaptan düşecek olursak;
    // son 10km'de 22 pothole yakalayarak badge kazanmaya hak kazandı!
    public void testCalculatePacmanHavuzProblemi() throws Exception {

        PacmanBadge.getInstance().clearPacmanQueue();

        assertEquals(PacmanBadge.INITIAL_DISTANCE, PacmanBadge.getInstance().calculatePacman( 0f, 0 ));
        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 1f, 1 ));

        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 4f, 7 ));
        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 6f, 8 ));
        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 9f, 8 ));

        assertEquals(PacmanBadge.POTHOLES_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 13f, 19 ));

        assertEquals(PacmanBadge.DISTANCE_ADDED_TO_QUEUE, PacmanBadge.getInstance().calculatePacman( 13f, 19 ));
        assertEquals(PacmanBadge.BADGE_ACHIEVED, PacmanBadge.getInstance().calculatePacman( 16f, 30 ));
    }
}