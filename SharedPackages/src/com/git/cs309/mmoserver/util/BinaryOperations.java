package com.git.cs309.mmoserver.util;

public final class BinaryOperations {
	public static final boolean checkMask(final byte value, final byte mask) {
		return (value & mask) != 0;
	}

	public static final boolean checkMask(final int value, final int mask) {
		return (value & mask) != 0;
	}

	public static final int[] intArrayFromBytes(final byte[] bytes) {
		if (bytes.length % 4 != 0)
			throw new IllegalArgumentException("Byte array must have a length that is a multiple of 4.");
		int[] ints = new int[bytes.length / 4];
		int index = 0;
		for (int i = 0; i < ints.length; i++) {
			ints[i] = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | (bytes[index++]);
		}
		return ints;
	}

	public static final int[] intArrayFromBytes(final byte[] bytes, final int startPosition) {
		if ((bytes.length - startPosition) % 4 != 0)
			throw new IllegalArgumentException(
					"Byte array must have a length that is a multiple of 4 when subtracted against the start position.");
		int[] ints = new int[(bytes.length - startPosition) / 4];
		int index = startPosition;
		for (int i = 0; i < ints.length; i++) {
			ints[i] = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | (bytes[index++]);
		}
		return ints;
	}

	public static final int[] intArrayFromBytes(final byte[] bytes, final int startPosition, final int count) {
		if ((bytes.length - startPosition) / 4 < count)
			throw new IllegalArgumentException(
					"Byte array must have a length that is at least the same length as the count when subtracted against the start position.");
		int[] ints = new int[count];
		int index = startPosition;
		for (int i = 0; i < ints.length; i++) {
			ints[i] = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | (bytes[index++]);
		}
		return ints;
	}

	public static final int intFromBytes(final byte[] bytes) {
		if (bytes.length < 4)
			throw new IllegalArgumentException("Byte array must be at least 4 bytes in length.");
		return (bytes[0] << 24) | (bytes[1] << 16) | (bytes[2] << 8) | (bytes[3]);
	}

	public static final int intFromBytes(final byte[] bytes, final int startPosition) {
		if (bytes.length - startPosition < 4)
			throw new IllegalArgumentException(
					"Byte array must be at least 4 bytes in length from the start position.");
		return (bytes[startPosition] << 24) | (bytes[startPosition + 1] << 16) | (bytes[startPosition + 2] << 8)
				| (bytes[startPosition + 3]);
	}

	public static final long[] longArrayFromBytes(final byte[] bytes) {
		if (bytes.length % 8 != 0)
			throw new IllegalArgumentException("Byte array must have a length that is a multiple of 8.");
		long[] longs = new long[bytes.length / 8];
		int index = 0;
		for (int i = 0; i < longs.length; i++) {
			longs[i] = (bytes[index++] << 56) | (bytes[index++] << 48) | (bytes[index++] << 40) | (bytes[index++] << 32)
					| (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | (bytes[index++]);
		}
		return longs;
	}

	public static final long[] longArrayFromBytes(final byte[] bytes, final int startPosition) {
		if ((bytes.length - startPosition) % 8 != 0)
			throw new IllegalArgumentException(
					"Byte array must have a length that is a multiple of 8 when subtracted against the start position.");
		long[] longs = new long[(bytes.length - startPosition) / 8];
		int index = startPosition;
		for (int i = 0; i < longs.length; i++) {
			longs[i] = (bytes[index++] << 56) | (bytes[index++] << 48) | (bytes[index++] << 40) | (bytes[index++] << 32)
					| (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | (bytes[index++]);
		}
		return longs;
	}

	public static final long[] longArrayFromBytes(final byte[] bytes, final int startPosition, final int count) {
		if ((bytes.length - startPosition) / 8 < count)
			throw new IllegalArgumentException(
					"Byte array must have a length that is at least the same length as the count when subtracted against the start position.");
		long[] longs = new long[count];
		int index = startPosition;
		for (int i = 0; i < longs.length; i++) {
			longs[i] = (bytes[index++] << 56) | (bytes[index++] << 48) | (bytes[index++] << 40) | (bytes[index++] << 32)
					| (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | (bytes[index++]);
		}
		return longs;
	}

	//Long section
	public static final long longFromBytes(final byte[] bytes) {
		if (bytes.length < 8)
			throw new IllegalArgumentException("Byte array must be at least 8 bytes in length.");
		return (bytes[0] << 56) | (bytes[1] << 48) | (bytes[2] << 40) | (bytes[3] << 32) | (bytes[4] << 24)
				| (bytes[5] << 16) | (bytes[6] << 8) | (bytes[7]);
	}

	public static final long longFromBytes(final byte[] bytes, final int startPosition) {
		if (bytes.length - startPosition < 8)
			throw new IllegalArgumentException(
					"Byte array must be at least 8 bytes in length from the start position.");
		return (bytes[startPosition] << 56) | (bytes[startPosition + 1] << 48) | (bytes[startPosition + 2] << 40)
				| (bytes[startPosition + 3] << 32) | (bytes[startPosition + 4] << 24) | (bytes[startPosition + 5] << 16)
				| (bytes[startPosition + 6] << 8) | (bytes[startPosition + 7]);
	}

	public static final byte[] toBytes(final int i) {
		return new byte[] { (byte) (i >> 24), (byte) ((i >> 16) & 0xFF), (byte) ((i >> 8) & 0xFF), (byte) (i & 0xFF) };
	}

	public static final byte[] toBytes(final int... intArray) {
		byte[] bytes = new byte[intArray.length * 4];
		int index = 0;
		for (int i : intArray) {
			bytes[index++] = (byte) (i >> 24);
			bytes[index++] = (byte) ((i >> 16) & 0xFF);
			bytes[index++] = (byte) ((i >> 8) & 0xFF);
			bytes[index++] = (byte) (i & 0xFF);
		}
		return bytes;
	}

	public static final byte[] toBytes(final long l) {
		return new byte[] { (byte) (l >> 56), (byte) ((l >> 48) & 0xFF), (byte) ((l >> 40) & 0xFF),
				(byte) ((l >> 32) & 0xFF), (byte) ((l >> 24) & 0xFF), (byte) ((l >> 16) & 0xFF),
				(byte) ((l >> 8) & 0xFF), (byte) (l & 0xFF) };
	}

	public static final byte[] toBytes(final long... longArray) {
		byte[] bytes = new byte[longArray.length * 4];
		int index = 0;
		for (long l : longArray) {
			bytes[index++] = (byte) (l >> 56);
			bytes[index++] = (byte) ((l >> 48) & 0xFF);
			bytes[index++] = (byte) ((l >> 40) & 0xFF);
			bytes[index++] = (byte) ((l >> 32) & 0xFF);
			bytes[index++] = (byte) ((l >> 24) & 0xFF);
			bytes[index++] = (byte) ((l >> 16) & 0xFF);
			bytes[index++] = (byte) ((l >> 8) & 0xFF);
			bytes[index++] = (byte) (l & 0xFF);
		}
		return bytes;
	}

	public static final byte toggleMask(final byte value, final byte mask) {
		return (byte) (value & ~mask);
	}

	public static final int toggleMask(final int value, final int mask) {
		return value & ~mask;
	}
}
