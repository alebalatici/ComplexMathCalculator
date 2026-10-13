import java.util.Objects;

public class ComplexNumber {
    private final double re;
    private final double im;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getReal() {
        return re;
    }

    public double getImag() {
        return im;
    }

    public String toString() {
        String reStr;
        if (re == (long) re) {
            reStr = String.valueOf((long) re);
        }

        else {
            reStr = String.valueOf(re);
        }

        String imStr;
        if (im == (long) im) {
            imStr = String.valueOf((long) im);
        }

        else {
            imStr = String.valueOf(im);
        }

        if (im == 0) {
            return reStr;
        }

        if (re == 0) {
            if (im == 1) {
                return "i";
            }

            else if (im == -1) {
                return "-i";
            }
            return imStr + "*i";
        }

        String sign;
        if (im < 0) {
            sign = "";
        }
        else {
            sign = "+";
        }

        if (im == 1) {
            return reStr + "+i";
        }

        if (im == -1) {
            return reStr + "-i";
        }
        return reStr + sign + imStr + "*i";
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final ComplexNumber other = (ComplexNumber) obj;
        return Double.compare(this.re, other.re) == 0 && Double.compare(this.im, other.im) == 0;
    }
}
