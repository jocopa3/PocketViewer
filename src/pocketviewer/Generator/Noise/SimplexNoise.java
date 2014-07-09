package pocketviewer.Generator.Noise;

import static pocketviewer.Generator.NoiseUtils.*;
/**
 *
 * @author Jocopa3
 */
public class SimplexNoise extends Noise{
	
    public int[] perm;
	
	public SimplexNoise(){
		perm = genPermArray();
	}
	
	public SimplexNoise(long seed){
		perm = genPermArray(seed);
	}

	@Override
    public float noise(float xin, float yin, float zin){
        float F3, G3, t, X0, Y0, Z0, x0, y0, z0, s, x1, y1, z1, x2, y2, z2, x3, y3, z3, t0, t1, t2, t3, n0, n1, n2, n3;
        int i, j, k, ii, jj, kk, i1, j1, k1, i2, j2, k2, gi0, gi1, gi2, gi3;

        F3 = 1.0f/3.0f;
        s = (xin+yin+zin)*F3;
        i = (int)(xin+s);
        j = (int)(yin+s);
        k = (int)(zin+s);
        G3 = 1.0f/6.0f;
        t = (i+j+k)*G3;
        X0 = i-t;Y0 = j-t;Z0 = k-t;
        x0 = xin-X0;y0 = yin-Y0;z0 = zin-Z0;

        //System.out.println("Values: "+s+" | "+i+" | "+j+" | "+k+" | "+t+" | "+X0+" | "+Y0+" | "+Z0+" | "+x0+" | "+y0+" | "+z0);

        if(x0 >= y0){
            if(y0 >= z0){i1=1; j1=0; k1=0; i2=1; j2=1; k2=0;}
            else if(x0 >= z0){i1=1; j1=0; k1=0; i2=1; j2=0; k2=1;}
            else{i1=0; j1=0; k1=1; i2=1; j2=0; k2=1;}
        }
        else{
            if(y0 < z0){i1=0; j1=0; k1=1; i2=0; j2=1; k2=1;}
            else if(x0 < z0){ i1=0; j1=1; k1=0; i2=0; j2=1; k2=1;}
            else{i1=0; j1=1; k1=0; i2=1; j2=1; k2=0;}
        }

        //System.out.println("Values 1: "+i1+" | "+j1+" | "+k1+" | "+i2+" | "+j2+" | "+k2);

        x1 = x0 - i1 + G3;
        y1 = y0 - j1 + G3;
        z1 = z0 - k1 + G3;
        x2 = x0 - i2 + 2.0f*G3;
        y2 = y0 - j2 + 2.0f*G3;
        z2 = z0 - k2 + 2.0f*G3;
        x3 = x0 - 1.0f + 3.0f*G3;
        y3 = y0 - 1.0f + 3.0f*G3;
        z3 = z0 - 1.0f + 3.0f*G3;

        ii = i & 255;
        jj = j & 255;
        kk = k & 255;

        //System.out.println(perm[kk]);
        //System.out.println("Values 2: "+perm[ii+perm[jj+perm[kk]]]+" | "+perm[ii+i1+perm[jj+j1+perm[kk+k1]]]+" | "+perm[ii+i2+perm[jj+j2+perm[kk+k2]]]+" | "+perm[ii+1+perm[jj+1+perm[kk+1]]]);

        gi0 = perm[ii+perm[jj+perm[kk]]] % 12;
        gi1 = perm[ii+i1+perm[jj+j1+perm[kk+k1]]] % 12;
        gi2 = perm[ii+i2+perm[jj+j2+perm[kk+k2]]] % 12;
        gi3 = perm[ii+1+perm[jj+1+perm[kk+1]]] % 12;

        t0 = 0.6f - x0*x0 - y0*y0 - z0*z0;
        if(t0<0){
             n0 = 0.0f;
        }
        else{
            t0 *= t0;
            n0 = t0 * t0 * dot(x0, y0, z0, grad3D[gi0]);
        }

        t1 = 0.6f - x1*x1 - y1*y1 - z1*z1;
        if(t1<0){
             n1 = 0.0f;
        }
        else{
            t1 *= t1;
            n1 = t1 * t1 * dot(x1, y1, z1, grad3D[gi1]);
        }

        t2 = 0.6f - x2*x2 - y2*y2 - z2*z2;
        if(t2<0){
             n2 = 0.0f;
        }
        else{
            t2 *= t2;
            n2 = t2 * t2 * dot(x2, y2, z2, grad3D[gi2]);
        }

        t3 = 0.6f - x3*x3 - y3*y3 - z3*z3;
        if(t3<0){
             n3 = 0.0f;
        }
        else{
            t3 *= t3;
            n3 = t3 * t3 * dot(x3, y3, z3, grad3D[gi3]);
        }

        //System.out.println("Values 3: xin: "+xin+" yin: "+yin+" zin: "+zin+" | "+x1+" | "+x2+" | "+x3+" | "+y1+" | "+y2+" | "+y3+" | "+z1+" | "+z2+" | "+z3+" | "+ii+" | "+
        //	jj+" | "+kk+" | "+gi0+" | "+gi1+" | "+gi2+" | "+gi3+" | "+t0+" | "+t1+" | "+t2+" | "+t3+" | "+n0+" | "+n1+" | "+n2+" | "+n3+" ");

        return 16.0f*(n0 + n1 + n2 + n3)+1.0f;
    }

	@Override
	public float octaveNoise(int octaves, float x, float y, float z){
		float value = 0.0f;
		for(int i=0; i<octaves; i++){
            value += noise(
				(x * (2 << (i - 1))),
				(y * (2 << (i - 1))),
				(z * (2 << (i - 1)))
			);
		}
		return value;
	}
}
