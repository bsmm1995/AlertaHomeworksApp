package control;

import java.util.Calendar;

public class Metodos {
    private int currentDia;
    private int currentMes;
    private int currentAnio;

    private int currentMinutos;

    public Metodos() {
        final Calendar c = Calendar.getInstance();
        this.currentMes = c.get(Calendar.MONTH) + 1;
        this.currentDia = c.get(Calendar.DAY_OF_MONTH);
        this.currentAnio = c.get(Calendar.YEAR);

        currentMinutos = c.get(Calendar.HOUR_OF_DAY) * 60;
        currentMinutos = currentMinutos + c.get(Calendar.MINUTE);
    }

    public int[] getHorasRestantes(String time) {

        String[] parts = time.split(":");
        int MINUTOS;
        MINUTOS = Integer.parseInt(parts[0]) * 60;
        MINUTOS = MINUTOS + Integer.parseInt(parts[1]);

        int HoraRestante;
        int MinutoRestante;
        MINUTOS = MINUTOS - currentMinutos;

        if (MINUTOS < 0) {
            return null;
        } else {
            HoraRestante = MINUTOS / 60;
            MinutoRestante = MINUTOS % 60;
        }

        return new int[]{HoraRestante, MinutoRestante};
    }

    public int[] getHorasOfManiana(String horaMañana) {
        int[] horasHoy = getHorasRestantes("24:00");
        String[] parts = horaMañana.split(":");
        horasHoy[0] = horasHoy[0] + Integer.parseInt(parts[0]);
        horasHoy[1] = horasHoy[1] + Integer.parseInt(parts[1]);
        if (horasHoy[1] > 59) {
            horasHoy[0]++;
            horasHoy[1] = horasHoy[1] - 60;
        }
        return horasHoy;
    }

    public int[] getDiasRestantes(String date) {

        String[] parts = date.split("-");

        int DiaRestante;
        int MesRestante;
        int AnioRestante;

        if (Integer.parseInt(parts[2]) >= currentAnio) {
            AnioRestante = Integer.parseInt(parts[2]) - currentAnio;
        } else {
            return null;
        }

        if (Integer.parseInt(parts[1]) >= currentMes && AnioRestante >= 0) {
            MesRestante = Integer.parseInt(parts[1]) - currentMes;
        } else if (AnioRestante > 0) {
            MesRestante = 12 - (currentMes - Integer.parseInt(parts[1]));
        } else {
            return null;
        }

        if (Integer.parseInt(parts[0]) >= currentDia && MesRestante >= 0) {
            DiaRestante = Integer.parseInt(parts[0]) - currentDia;
        } else if (MesRestante > 0) {
            DiaRestante = 30 - (currentDia - Integer.parseInt(parts[0]));
        } else {
            return null;
        }

        return new int[]{DiaRestante, MesRestante, AnioRestante};
    }

    public int getCurrentTimeInMinutos() {
        return this.currentMinutos;
    }
}
